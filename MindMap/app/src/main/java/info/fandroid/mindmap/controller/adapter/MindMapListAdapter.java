package info.fandroid.mindmap.controller.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.fandroid.mindmap.R;
import info.fandroid.mindmap.database.DBManager;
import info.fandroid.mindmap.model.ModelMap;
import info.fandroid.mindmap.model.ModelPlanet;
import info.fandroid.mindmap.ui.activity.OpenedMapActivity;
import info.fandroid.mindmap.ui.dialog.SupportingDialog;
import info.fandroid.mindmap.ui.fragment.EditMindMapFragment;
import info.fandroid.mindmap.util.FragmentManagerHelper;
import info.fandroid.mindmap.util.Utils;

/**
 * Created by Vitaly on 26.12.2015.
 */
public class MindMapListAdapter extends RecyclerView.Adapter<MindMapListAdapter.ViewHolder> {

    private List<ModelMap> mindMapList;
    private Context mContext;
    private DBManager mDbManager;

    private FragmentManagerHelper fragmentManagerHelper;


    public MindMapListAdapter() {
        mindMapList = new ArrayList<>();
        mDbManager = DBManager.getInstance();
        fragmentManagerHelper = FragmentManagerHelper.getInstance();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView descriptionView;
        public TextView description;
        public TextView lastChanged;
        public CircleImageView circle;
        public TextView planetsCount;
        public ImageView subject;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.tv_model_list_element_name);
            descriptionView = (TextView) v.findViewById(R.id.tv_model_list_element_description);
            description = (TextView) v.findViewById(R.id.tv_model_list_element_last_description_text);
            lastChanged = (TextView) v.findViewById(R.id.tv_model_list_element_last_changed_date);
            circle = (CircleImageView) v.findViewById(R.id.cv_model_list_element_circle);
            planetsCount = (TextView) v.findViewById(R.id.tv_model_list_element_last_count_number);
            subject = (ImageView) v.findViewById(R.id.iv_model_list_element_subject);
        }
    }

    @Override
    public MindMapListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_mind_map_list_element, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final ModelMap model = mindMapList.get(position);

        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.lastChanged.setText(Utils.getDateWithCurrentLocale(model.getLastModified(),
                mContext));
        holder.planetsCount.setText(String.valueOf(model.getPlanetsCount()));

        holder.subject.setImageDrawable(model.getSubject().getBigIcon(mContext));

        if (model.getDescription().equals("") && holder.descriptionView
                .getVisibility() == View.VISIBLE) {
            holder.descriptionView.setVisibility(View.INVISIBLE);
        } else if (holder.descriptionView.getVisibility() != View.VISIBLE) {
            holder.descriptionView.setVisibility(View.VISIBLE);
        }

        holder.circle.setColorFilter(model.getColor());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OpenedMapActivity.class);
                intent.putExtras(ModelPlanet.mapToBundle(model));
                mContext.startActivity(intent);
                ((AppCompatActivity) mContext).overridePendingTransition(0, 0);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                SupportingDialog.longClickDialog(mContext, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            EditMindMapFragment editMindMapFragment = new EditMindMapFragment();
                            editMindMapFragment.setArguments(ModelMap.toBundle(mindMapList.get(holder.getLayoutPosition())));
                            fragmentManagerHelper.setFragment(R.id.relative_container, editMindMapFragment, true);
                            editMindMapFragment.setOnFragmentListener(new EditMindMapFragment.OnEditFragmentListener() {
                                @Override
                                public void onBackFromEdit(ModelMap model) {
                                    ModelMap old = mindMapList.get(holder.getAdapterPosition());

                                    old.setName(model.getName());
                                    old.setDescription(model.getDescription());
                                    old.setColor(model.getColor());
                                    old.setLastModified(model.getLastModified());
                                    old.setSubject(model.getSubject());

                                    notifyItemChanged(holder.getAdapterPosition());
                                    ((AppCompatActivity) mContext).onBackPressed();
                                }
                            });

                        } else if (which == 1) {
                            SupportingDialog.removeDialog(mContext, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int removedPosition = holder.getLayoutPosition();
                                    mDbManager.delete().map(mindMapList.get(removedPosition).getId());

                                    mindMapList.remove(removedPosition);
                                    notifyItemRemoved(removedPosition);

                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                });
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mindMapList.size();
    }

    public void addMindMap(ModelMap mindMapListElement) {
        mindMapList.add(mindMapListElement);
        notifyItemInserted(mindMapList.size());
    }

    public void addMapList(List<ModelMap> maps) {
        mindMapList.addAll(maps);
        notifyItemRangeInserted(0, maps.size());
    }
}
