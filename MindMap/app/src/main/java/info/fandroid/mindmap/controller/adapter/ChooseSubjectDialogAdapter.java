package info.fandroid.mindmap.controller.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import info.fandroid.mindmap.R;
import info.fandroid.mindmap.model.ModelMap;

/**
 * Created by Vitaly on 28.12.2015.
 */
public class ChooseSubjectDialogAdapter extends
        RecyclerView.Adapter<ChooseSubjectDialogAdapter.ViewHolder>{

    private ModelMap.Subject[] subjects;

    private Context mContext;

    private OnSubjectClickListener onSubjectClickListener;


    public interface OnSubjectClickListener {
        void onClick(ModelMap.Subject subject);
    }

    public ChooseSubjectDialogAdapter() {
        subjects = ModelMap.Subject.values();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageButton icon;
        RelativeLayout divider;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tv_subject_list_element_name);
            icon = (ImageButton) itemView.findViewById(R.id.ib_subject_list_element_icon);
            divider = (RelativeLayout) itemView.findViewById(R.id.divider);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_subject_list_element, parent, false);


        ViewHolder vh = new ViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final ModelMap.Subject subject = subjects[position];

        holder.name.setText(subject.name());
        holder.icon.setImageDrawable(subject.getSmallIcon(mContext));

        if (position == subjects.length - 1 && holder.divider.getVisibility() == View.VISIBLE) {
            holder.divider.setVisibility(View.INVISIBLE);
        } else if (holder.divider.getVisibility() != View.VISIBLE) {
            holder.divider.setVisibility(View.VISIBLE);
        }

        holder.icon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        holder.icon.setColorFilter(mContext.getResources().getColor(R.color.colorAccent));
                        break;
                    case MotionEvent.ACTION_UP:
                        holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.color_click));



                        new Handler().postDelayed(new Runnable() {


                            @Override
                            public void run() {
                                holder.icon.setColorFilter(mContext.getResources().getColor(R.color.colorTitleOnWhiteBackground));


                                onSubjectClickListener.onClick(subject);


                            }
                        }, 50);
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        holder.icon.setColorFilter(mContext.getResources().getColor(R.color.colorTitleOnWhiteBackground));
                        break;
                }




                return false;
            }
        });

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }

    public void setOnSubjectClickListener(OnSubjectClickListener onSubjectClickListener) {
        this.onSubjectClickListener = onSubjectClickListener;
    }

    @Override
    public int getItemCount() {
        return subjects.length;
    }



}
