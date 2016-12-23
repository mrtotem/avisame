package com.totem.avisame.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.totem.avisame.R;
import com.totem.avisame.activities.MainActivity;
import com.totem.avisame.models.Message;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<Message> mMessages;
    private Context mContext;

    public MessagesAdapter(Context _context, List<Message> messages) {
        this.mContext = _context;
        this.mMessages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.adapter_message, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Message temp = mMessages.get(position);

        if (temp.getDate() != null) holder.mDate.setText(temp.getDate());
        if (temp.getMessage() != null) holder.mMessage.setText(temp.getMessage());
        if (temp.getLatitude() != null && temp.getLongitude() != null) {

            holder.mLinkToMap.setVisibility(View.VISIBLE);
            holder.mLinkToMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (temp.getLatitude() != null && temp.getLongitude() != null) {

                        String labelLocation = "Usuario Avisame!";

                        Uri gmmIntentUri = Uri.parse("geo:<" + temp.getLatitude() + ">,<" + temp.getLongitude() + ">?q=<" + temp.getLatitude() + ">,<" + temp.getLongitude() + ">(" + labelLocation + ")");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        ((MainActivity) mContext).onLocationShow(mapIntent);
                    }
                }
            });
        } else {
            holder.mLinkToMap.setVisibility(View.GONE);
        }

        if (temp.getType().equals(String.valueOf(1))) {

            holder.mType.setText("ALERTA");
            holder.mType.setTextColor(mContext.getResources().getColor(android.R.color.holo_orange_dark));
        } else if (temp.getType().equals(String.valueOf(2))) {

            holder.mType.setText("PELIGRO");
            holder.mType.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_dark));
        } else if (temp.getType().equals(String.valueOf(3))) {

            holder.mType.setText("LLEGADA");
            holder.mType.setTextColor(mContext.getResources().getColor(android.R.color.holo_green_dark));
        }
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mMessage;
        private TextView mLinkToMap;
        private TextView mDate;
        private TextView mType;

        ViewHolder(View itemView) {
            super(itemView);
            mDate = (TextView) itemView.findViewById(R.id.message_date);
            mMessage = (TextView) itemView.findViewById(R.id.message);
            mLinkToMap = (TextView) itemView.findViewById(R.id.link_map);
            mType = (TextView) itemView.findViewById(R.id.message_type);
        }
    }

    public interface MessageActions {

        void onLocationShow(Intent mapIntent);
    }
}
