package info.fandroid.mindmap.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import info.fandroid.mindmap.R;
import info.fandroid.mindmap.controller.adapter.ChooseSubjectDialogAdapter;
import info.fandroid.mindmap.model.ModelMap;

/**
 * Created by Vitaly on 28.12.2015.
 */
public class ChooseSubjectDialogFragment extends DialogFragment {


    ChooseSubjectDialogListener mListener;

    public interface ChooseSubjectDialogListener {
        void onSubjectDialogPositiveClick(ModelMap.Subject subject);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customTitle = inflater.inflate(R.layout.dialog_choose_subject_title, null);
        View customBody = inflater.inflate(R.layout.dialog_choose_subject_body, null);

        builder.setCustomTitle(customTitle);
        builder.setView(customBody);

        RecyclerView rvSubjectList = (RecyclerView) customBody.findViewById(R.id.rv_subject_list);

        rvSubjectList.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvSubjectList.setLayoutManager(layoutManager);

        ChooseSubjectDialogAdapter adapter = new ChooseSubjectDialogAdapter();
        rvSubjectList.setAdapter(adapter);

        adapter.setOnSubjectClickListener(new ChooseSubjectDialogAdapter.OnSubjectClickListener() {
            @Override
            public void onClick(ModelMap.Subject subject) {
                if (mListener != null) {
                    mListener.onSubjectDialogPositiveClick(subject);
                }
                dismiss();
            }
        });

        return builder.create();
    }


    public void setOnChooseSubjectListener(ChooseSubjectDialogListener chooseSubjectListener) {
        this.mListener = chooseSubjectListener;
    }


}
