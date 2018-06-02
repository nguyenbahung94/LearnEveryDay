/*
 * Copyright (C) 2015 Google Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.ekakashi.greenhouse.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.common.OnItemClickListener;
import com.google.android.gms.location.places.AutocompletePrediction;

import java.util.List;

/**
 * Adapter that handles Autocomplete requests from the Places Geo Data Client.
 * {@link AutocompletePrediction} results from the API are frozen and stored directly in this
 * adapter. (See {@link AutocompletePrediction#freeze()}.)
 */
public class PlaceAutocompleteAdapter extends RecyclerView.Adapter<PlaceAutocompleteAdapter.myViewHolder> {
    private List<PlaceObject> listPlace;
    private OnItemClickListener onItemClickListener;

    public PlaceAutocompleteAdapter(Context context, List<PlaceObject> listPlace, OnItemClickListener onItemClickListener) {
        this.listPlace = listPlace;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_autocompelte_textview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (listPlace.size() != 0) {
            holder.tvTitle.setText(listPlace.get(position).getPlaceName());
            holder.tvSubTitle.setText(listPlace.get(position).getTitleName());
            holder.llParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnItemClick(view, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listPlace.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvSubTitle;
        private LinearLayout llParent;

        public myViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubTitle = itemView.findViewById(R.id.tvSubTitle);
            llParent = itemView.findViewById(R.id.llParent);
        }
    }

}
