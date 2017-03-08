package me.codpoe.note.editor;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.codpoe.note.R;

/**
 * Created by Codpoe on 2016/12/12.
 */

public class ThemeRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private String[] mThemeNames;
    private int mCurrentSelected;
    private int[] mThemeImgs;
    private OnItemClickListener mListener;

    public ThemeRvAdapter(Context context, String[] themeNames, int[] themeImgs, int currentSelected) {
        mContext = context;
        mThemeNames = themeNames;
        mThemeImgs = themeImgs;
        mCurrentSelected = currentSelected;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_theme, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // restore state
        ((ViewHolder) holder).themeImg.setBorderColor(mContext.getResources().getColor(android.R.color.white));

        // bind
        if (position < 6) {
            ((ViewHolder) holder).themeImg.setImageDrawable(new ColorDrawable(mContext.getResources().getColor(mThemeImgs[position])));
        } else {
            Glide.with(mContext)
                    .load(mThemeImgs[position])
                    .into(((ViewHolder) holder).themeImg);
        }
        ((ViewHolder) holder).themeTv.setText(mThemeNames[position]);

        // set selected
        if (mCurrentSelected == position) {
            ((ViewHolder) holder).themeImg.setBorderColor(mContext.getResources().getColor(R.color.colorMain));
        } else {
            ((ViewHolder) holder).themeImg.setBorderColor(mContext.getResources().getColor(android.R.color.white));
        }

        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCurrentSelected = position;
                    notifyDataSetChanged();
                    mListener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mThemeNames.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.theme_img)
        CircleImageView themeImg;
        @BindView(R.id.theme_tv)
        TextView themeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
