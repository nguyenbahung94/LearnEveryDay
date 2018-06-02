package com.ekakashi.greenhouse.features.invite_member;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;

import java.util.ArrayList;

/**
 * Created by nquochuy on 1/24/2018.
 */

public class InviteMemberAdapter extends RecyclerView.Adapter<InviteMemberAdapter.MemberInviteViewHolder> {

    private ArrayList<InviteMemberModel> list;
    private LayoutInflater layoutInflater;
    private InviteMemberInterface.SelectedAuthority mSelectedAuthority;

    public InviteMemberAdapter(ArrayList<InviteMemberModel> list, InviteMemberInterface.SelectedAuthority mSelectedAuthority, Context context) {
        this.list = list;
        this.mSelectedAuthority = mSelectedAuthority;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public InviteMemberAdapter.MemberInviteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_invite_member, parent, false);
        return new MemberInviteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MemberInviteViewHolder holder, final int position) {
        holder.vDivider.setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        final InviteMemberModel item = list.get(position);
        holder.tvMemberRole.setText(item.getRoleName());
        if (!TextUtils.isEmpty(item.getDescription())) {
            holder.tvMembeDescription.setVisibility(View.VISIBLE);
            holder.tvMembeDescription.setText(item.getDescription());
        } else {
            holder.tvMembeDescription.setVisibility(View.GONE);
        }

        holder.imgSelected.setVisibility(item.isSelected() ? View.VISIBLE : View.INVISIBLE);

        holder.layoutInviteMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearListSelected();
                mSelectedAuthority.onSelectedAuthority(item.getAuthority());
                item.setSelected(true);
                notifyDataSetChanged();
            }
        });
    }

    private void clearListSelected() {
        for (InviteMemberModel item : list) {
            item.setSelected(false);
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MemberInviteViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout layoutInviteMember;
        private ImageView imgSelected;
        private TextView tvMemberRole, tvMembeDescription;
        private View vDivider;

        public MemberInviteViewHolder(View itemView) {
            super(itemView);
            layoutInviteMember = itemView.findViewById(R.id.layoutInviteMember);
            imgSelected = itemView.findViewById(R.id.imgSelected);
            tvMemberRole = itemView.findViewById(R.id.tvMemberRole);
            tvMembeDescription = itemView.findViewById(R.id.tvMembeDescription);
            vDivider = itemView.findViewById(R.id.vDivider);
        }
    }
}
