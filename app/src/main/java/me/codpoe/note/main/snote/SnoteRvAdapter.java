package me.codpoe.note.main.snote;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.codpoe.note.R;
import me.codpoe.note.model.entity.Note;

/**
 * Created by Codpoe on 2016/12/6.
 */

public class SnoteRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Note> mNoteList;
    private OnItemClickListener mListener;

    public SnoteRvAdapter(Context context, List<Note> noteList) {
        mContext = context;
        mNoteList = noteList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_snote, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (mNoteList.get(position).getName() == null || mNoteList.get(position).getName().equals("")) {
            ((ViewHolder)holder).line.setVisibility(View.GONE);
            ((ViewHolder)holder).titleTv.setVisibility(View.GONE);
        } else {
            ((ViewHolder)holder).line.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).titleTv.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).titleTv.setText(mNoteList.get(position).getName());
        }
        switch (mNoteList.get(position).getTheme()) {
            case "light":
                changeTheme((ViewHolder) holder, R.color.colorMain, R.color.theme_light_text);
                break;
            case "dark":
                changeTheme((ViewHolder) holder, R.color.theme_dark_bg, R.color.theme_dark_text);
                break;
            case "green":
                changeTheme((ViewHolder) holder, R.color.theme_green_bg, R.color.theme_light_text);
                break;
            case "cyan":
                changeTheme((ViewHolder) holder, R.color.theme_cyan_bg, R.color.theme_light_text);
                break;
            case "pink":
                changeTheme((ViewHolder) holder, R.color.theme_pink_bg, R.color.theme_light_text);
                break;
            case "purple":
                changeTheme((ViewHolder) holder, R.color.theme_purple_bg, R.color.theme_light_text);
                break;
            case "wood":
                changeTheme((ViewHolder) holder, R.color.theme_wood_bg, R.color.theme_light_text);
                break;
            case "paper":
                changeTheme((ViewHolder) holder, R.color.theme_paper_bg, R.color.theme_light_text);
                break;
            default:
                break;
        }
        ((ViewHolder)holder).contentTv.setText(mNoteList.get(position).getContent());

        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(view, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mListener.onItemLongClick(view, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title_tv)
        TextView titleTv;
        @BindView(R.id.content_tv)
        TextView contentTv;
        @BindView(R.id.snote_card)
        CardView snoteCard;
        @BindView(R.id.line)
        View line;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private void changeTheme(ViewHolder holder, int backgroundColor, int textColor) {
        holder.snoteCard.setCardBackgroundColor(mContext.getResources().getColor(backgroundColor));
        holder.titleTv.setTextColor(mContext.getResources().getColor(textColor));
        holder.contentTv.setTextColor(mContext.getResources().getColor(textColor));
    }
}
