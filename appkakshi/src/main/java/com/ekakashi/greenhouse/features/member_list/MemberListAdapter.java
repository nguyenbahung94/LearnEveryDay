package com.ekakashi.greenhouse.features.member_list;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.Utils;

import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MemberListViewHolder> {

    private List<MemberListModel> list;
    private LayoutInflater layoutInflater;
    private Context context;
    private boolean isEditMode;
    private MemberListInterface.MemberListAdapterCallback mCallback;

    MemberListAdapter(Context context, List<MemberListModel> listModels, boolean isEditMode, MemberListInterface.MemberListAdapterCallback mCallback) {
        this.context = context;
        this.list = listModels;
        this.mCallback = mCallback;
        this.isEditMode = isEditMode;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MemberListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberListViewHolder(layoutInflater.inflate(R.layout.item_member, parent, false));
    }

    @Override
    public void onBindViewHolder(MemberListViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        MemberListModel item = list.get(pos);
        if (!TextUtils.isEmpty(item.getNickName())) {
            holder.tvUsername.setText(item.getNickName());
        } else if (!TextUtils.isEmpty(item.getUserName())) {
            holder.tvUsername.setText(item.getUserName());
        } else {
            holder.tvUsername.setText(item.getEmail());
        }

        if (Utils.INVITATION_STATUS_PENDING.equalsIgnoreCase(item.getInvitationStatus())) {
            holder.tvApproval.setText(context.getString(R.string.member_pending_approval));
            holder.tvApproval.setTextColor(context.getResources().getColor(R.color.pending_approval));
        } else {
            if (Utils.Role.ADMIN == item.getAuthority()) {
                holder.tvApproval.setText(R.string.authority_admin);
            } else if (Utils.Role.WORKER == item.getAuthority()) {
                holder.tvApproval.setText(R.string.authority_worker);
            } else {
                holder.tvApproval.setText(R.string.authority_user);
            }
            holder.tvApproval.setTextColor(context.getResources().getColor(R.color.tvColor44));
        }

        Glide.with(context).load(item.getUrlImage()).error(R.drawable.ic_user_default).into(holder.imgAvatar);
        holder.layoutMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onMemberClick(pos);
            }
        });

        if (isEditMode) {
            holder.imgDelete.setVisibility(View.VISIBLE);
            holder.imgChevron.setVisibility(View.GONE);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onDeleteMember(pos);
                }
            });
        } else {
            holder.imgDelete.setVisibility(View.GONE);
            holder.imgChevron.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class MemberListViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private TextView tvApproval;
        private ImageView imgAvatar;
        private ImageView imgDelete;
        private ImageView imgChevron;
        private CardView layoutMember;

        MemberListViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvApproval = itemView.findViewById(R.id.tvApproval);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgChevron = itemView.findViewById(R.id.imgChevron);
            layoutMember = itemView.findViewById(R.id.layoutMember);
        }
    }
}
