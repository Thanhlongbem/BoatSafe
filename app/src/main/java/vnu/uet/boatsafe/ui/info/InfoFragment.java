package vnu.uet.boatsafe.ui.info;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.OnClick;
import vnu.uet.boatsafe.R;
import vnu.uet.boatsafe.data.prefs.Preference;
import vnu.uet.boatsafe.di.component.ActivityComponent;
import vnu.uet.boatsafe.service.log.LoggerManager;
import vnu.uet.boatsafe.ui.base.BaseFragment;
import vnu.uet.boatsafe.ui.base.OnItemClickListener;
import vnu.uet.boatsafe.ui.home.HomeFragment;
import vnu.uet.boatsafe.ui.main.MainActivity;
import vnu.uet.boatsafe.ui.widget.SettingLanguageBottomDialog;
import vnu.uet.boatsafe.ui.widget.UiToolbarHome;
import vnu.uet.boatsafe.utils.AppConstants;
import vnu.uet.boatsafe.utils.AppUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoFragment extends BaseFragment implements InfoFrMvpView, SettingLanguageBottomDialog.Callback {

    public static final String TAG = InfoFragment.class.getSimpleName();

    @Inject
    InfoFrMvpPresent<InfoFrMvpView> presenter;

    @BindView(R.id.toolbar)
    UiToolbarHome toolBar;
    @BindView(R.id.btnSettingLanguage)
    Button btnSettingLanguage;
    @BindView(R.id.btnSettingCollision)
    Button btnSettingCollision;

    private SettingLanguageBottomDialog settingLanguageBottomDialog;
    private ArrayList<String> listLanguage;
    private Locale myLocale;
    private String currentLanguage;
    private int languageState;
    private String titleLanguageDilog;

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int configView() {
        return R.layout.fragment_info;
    }

    @Override
    protected void init(View v, Bundle savedInstanceState) {
        toolBar.setNameFragment(getString(R.string.title_info));
        listLanguage = new ArrayList<>();
        listLanguage.add("Tiếng Việt");
        listLanguage.add("English");

        languageState = Preference.buildInstance(getContext()).getLanguageState();

        if(languageState == 0){
            titleLanguageDilog ="Bạn hãy chọn ngôn ngữ cho ứng dụng";
            currentLanguage = "vi";
        }else {
            titleLanguageDilog ="Please choose languge for app";
            currentLanguage = "en";
        }
    }

    @Override
    protected void initCreatedView(View v) {
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnbinder(ButterKnife.bind(this, v));
            presenter.onAttach(this);
        }
    }

    @OnClick(R.id.btnSendEmail)
    public void OnButtonSendClick(){
        // Build an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // String array for alert dialog multi choice items
        String[] colors = new String[]{AppConstants.LIST_FILE_LOGGER.get(0), AppConstants.LIST_FILE_LOGGER.get(1), AppConstants.LIST_FILE_LOGGER.get(2), AppConstants.LIST_FILE_LOGGER.get(3), AppConstants.LIST_FILE_LOGGER.get(4),AppConstants.LIST_FILE_LOGGER.get(5),AppConstants.LIST_FILE_LOGGER.get(6), AppConstants.LIST_FILE_LOGGER.get(7)};
        // Boolean array for initial selected items
        final boolean[] checkedFiles = new boolean[]{true, true, true, true, true, true, true, true };
        // Convert the color array to list
        final List<String> filesList = Arrays.asList(colors);
        builder.setMultiChoiceItems(colors, checkedFiles, (dialog, which, isChecked) -> {

            // Update the current focused item's checked status
            checkedFiles[which] = isChecked;

            // Get the current focused item
            String currentItem = filesList.get(which);
        });
        // Specify the dialog is not cancelable
        builder.setCancelable(false);
        // Set a title for alert dialog
        builder.setTitle("Choose files?");
        // Set the positive/yes button click listener
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Do something when click positive button
            Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // set the type to 'email'
            emailIntent .setType("vnd.android.cursor.dir/email");
            String to[] = {"thanhlongny@gmail.com"};
            emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
            // the mail subject
            emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Subject");
            ArrayList<Uri> uris = new ArrayList<Uri>();
            for (int i = 0; i<checkedFiles.length; i++){
                boolean checked = checkedFiles[i];
                if (checked) {
                    //tv.setText(tv.getText() + colorsList.get(i) + "\n");
                    // the attachment
                    File logFile = new File(getContext().getExternalFilesDir("logs"), filesList.get(i));
                    Uri logFileUri = Uri.fromFile(logFile);
                    Log.e("OnButtonSendClick",logFile.toString());
                    uris.add(logFileUri);
                }
            }
            emailIntent .putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
            startActivity(Intent.createChooser(emailIntent , "Send email..."));
        });

        // Set the negative/no button click listener
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // Do something when click the negative button
        });


        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }

    @OnClick(R.id.btnSettingLanguage)
    public void onSettingLanguageClick(){
        if (settingLanguageBottomDialog != null) {
            settingLanguageBottomDialog.dismiss();
            settingLanguageBottomDialog = null;
        }
        settingLanguageBottomDialog = SettingLanguageBottomDialog.newInstance(titleLanguageDilog, listLanguage);
        settingLanguageBottomDialog.setCallBack(this).show(getActivity().getSupportFragmentManager(), SettingLanguageBottomDialog.TAG);
    }

    public void setLocale(String localeName){
        if (!localeName.equals(currentLanguage)) {
            myLocale = new Locale(localeName);
            Resources resources = getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            configuration.locale = myLocale;
            resources.updateConfiguration(configuration, displayMetrics);
            Intent refresh = new Intent(getContext(), MainActivity.class);
            refresh.putExtra(currentLanguage, localeName);
            startActivity(refresh);
            getActivity().finish();
            getActivity().startActivity(getActivity().getIntent());
        } else {
            if(Preference.buildInstance(getContext()).getLanguageState() == 0){
                Toast.makeText(getContext(), "Ngôn ngữ này đang được hiển thị", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(), "Language already selected!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onJustStringDialogClickListener(int position) {
        if(position == 0){
            if(Preference.buildInstance(getContext()).getLanguageState() == 0){
                Toast.makeText(getContext(), "Ngôn ngữ này đang được hiển thị", Toast.LENGTH_SHORT).show();
            }else {
                setLocale("vi");
                Preference.buildInstance(getContext()).setLanguageState(0);
            }
            //getActivity().finish();
            //AppUtils.replaceFragmentToActivity(getActivity().getSupportFragmentManager(), InfoFragment.newInstance(), R.id.vpListFragment, false, InfoFragment.TAG);
        }else {
            if(Preference.buildInstance(getContext()).getLanguageState() == 1){
                Toast.makeText(getContext(), "This language is already selected!", Toast.LENGTH_SHORT).show();
            }else {
                setLocale("en");
                Preference.buildInstance(getContext()).setLanguageState(1);
            }
            //getActivity().finish();
            //AppUtils.replaceFragmentToActivity(getActivity().getSupportFragmentManager(), InfoFragment.newInstance(), R.id.vpListFragment, false, InfoFragment.TAG);
        }
    }
}
