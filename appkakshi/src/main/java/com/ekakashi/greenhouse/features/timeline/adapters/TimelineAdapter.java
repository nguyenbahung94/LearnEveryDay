package com.ekakashi.greenhouse.features.timeline.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.ekakashi.greenhouse.R;
import com.ekakashi.greenhouse.api.APIManager;
import com.ekakashi.greenhouse.application.App;
import com.ekakashi.greenhouse.common.DateTimeNow;
import com.ekakashi.greenhouse.common.Utils;
import com.ekakashi.greenhouse.features.advice.AdviceDescriptionObject;
import com.ekakashi.greenhouse.features.advice.AdviceDetailActivity;
import com.ekakashi.greenhouse.features.timeline.TimelineInterface;
import com.ekakashi.greenhouse.features.timeline.image.TimelineViewImageActivity;
import com.ekakashi.greenhouse.features.timeline.models.TimelineResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.MyViewHolder> {

    private LinkedHashMap<String, TimelineResponse.ListTimeline> list;
    private Context context;
    private int userId;
    private LayoutInflater mLayoutInflater;
    private TimelineInterface.TimelineAdapterCallback mCallback;

    private int ID_NONE = 1;
    private static final int LIMIT_LINE_READ_MORE = 5;

    private int widthMeasureSpec;
    private int heightMeasureSpec;
    private int heightOfEachLine;
    private int paddingFirstLine;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
    public TimelineAdapter(final Context context, TimelineInterface.TimelineAdapterCallback timelineAdapterCallback,
                           LinkedHashMap<String, TimelineResponse.ListTimeline> list, int userId) {
        this.list = list;
        mCallback = timelineAdapterCallback;
        this.context = context;
        this.userId = userId;
        mLayoutInflater = LayoutInflater.from(context);
        calculateHeightOfEachLine();
    }

    private TimelineResponse.ListTimeline getByIndex(int index) {
        return (TimelineResponse.ListTimeline) list.values().toArray()[index];
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
    private void calculateHeightOfEachLine() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int deviceWidth = size.x;
        widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST);
        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        int heightOfFirstLine = getHeightOfTextView("A");//1 line = 76; 2 lines = 76 + 66; 3 lines = 76 + 66 + 66
        int heightOfSecondLine = getHeightOfTextView("A\nA") - heightOfFirstLine;
        paddingFirstLine = heightOfFirstLine - heightOfSecondLine;
        heightOfEachLine = heightOfSecondLine;
    }

    private int getHeightOfTextView(String text) {
        // Getting height of text view before rendering to layout
        // https://stackoverflow.com/questions/19908003/getting-height-of-text-view-before-rendering-to-layout/20087258
        TextView textView = new TextView(context);
        textView.setPadding(10, 0, 10, 0);
        //textView.setTypeface(typeface);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.tv_size_14sp));
        textView.setText(text, TextView.BufferType.SPANNABLE);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        return textView.getMeasuredHeight();
    }

    private int getLineCountOfTextViewBeforeRendering(String text) {
        return (getHeightOfTextView(text) - paddingFirstLine) / heightOfEachLine;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_timeline, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final TimelineResponse.ListTimeline item = getByIndex(position);
        if (item.getTimelineType() == Utils.Constant.TIMELINE_TYPE_DIARY) {
            configTimelineDiary(holder, position, item);
            holder.linAdviceLink.setVisibility(View.GONE);
        } else {
            holder.imgContent1.setVisibility(View.GONE);
            holder.layoutImage2.setVisibility(View.GONE);
            holder.layoutImage3.setVisibility(View.GONE);
            holder.imgContent31.setVisibility(View.GONE);
            holder.imgContent32.setVisibility(View.GONE);
            holder.imgContent33.setVisibility(View.GONE);
            holder.layoutLocation.setVisibility(View.GONE);
            holder.tableData.setVisibility(View.GONE);
            holder.imgToolbarInfo.setVisibility(View.GONE);
            holder.tvReadMore.setVisibility(View.GONE);
            //
            holder.tvName.setText(item.getFieldName());
            holder.tvTime.setText(DateTimeNow.compareTime(context, item.getDateTimeline()));

            if (item.getTimelineType() == Utils.Constant.TIMELINE_TYPE_NOTIFICATION) {
                configTimelineNotification(holder, position, item);
                holder.linAdviceLink.setVisibility(View.GONE);
            } else if (item.getTimelineType() == Utils.Constant.TIMELINE_TYPE_ADVICE) {
                configTimelineAdvice(holder, item);
            } else {
                //remote control.
                Glide.with(context).load(R.drawable.ic_timeline_notification).into(holder.imgAvatar);
            }
        }
    }

    private void configTimelineDiary(MyViewHolder holder, final int position, final TimelineResponse.ListTimeline item) {
        final TimelineResponse.Diary diary = item.getDiary();
        int lineLeft = LIMIT_LINE_READ_MORE;

        if (diary != null) {
            Glide.with(context).load(diary.getUserImageUrl()).placeholder(R.drawable.ic_user_default)
                    .error(R.drawable.ic_user_default).into(holder.imgAvatar);
            if (!TextUtils.isEmpty(diary.getNickName())) {
                holder.tvName.setText(diary.getNickName());
            } else {
                holder.tvName.setText(diary.getUserName());
            }

            if (diary.getDiaryImageUrl() != null) {
                //TODO fix bug
                showImage(holder, position, diary.getDiaryImageUrl());
            } else {
                holder.imgContent1.setVisibility(View.GONE);
                holder.layoutImage2.setVisibility(View.GONE);
                holder.layoutImage3.setVisibility(View.GONE);
                holder.imgContent31.setVisibility(View.GONE);
                holder.imgContent32.setVisibility(View.GONE);
                holder.imgContent33.setVisibility(View.GONE);
            }

            //If this diary is user's own
            if (userId == diary.getUserId()) {
                holder.imgToolbarInfo.setVisibility(View.VISIBLE);
                holder.imgToolbarInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogEditDiary(item.getTimelineId(), position);
                    }
                });
            } else {
                holder.imgToolbarInfo.setVisibility(View.GONE);
            }

            //Show data
                /*Target Crop:
                Work Type:
                Disease:
                Pesticide:
                Fertilizer:
                */
            lineLeft -= getLineCountOfTextViewBeforeRendering(item.getDescription());
            holder.tableData.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(diary.getTargetCrop())) {
                holder.rowTargetCrop.setVisibility(View.GONE);
            } else {
                if (lineLeft > 0) {
                    holder.rowTargetCrop.setVisibility(View.VISIBLE);
                    holder.tvTargetCrop.setText(diary.getTargetCrop());
                    holder.tvReadMore.setVisibility(lineLeft > 0 ? View.GONE : View.VISIBLE);
                    lineLeft -= 1;
                } else {
                    holder.rowTargetCrop.setVisibility(View.GONE);
                    holder.tvReadMore.setVisibility(lineLeft == 0 ? View.VISIBLE : View.GONE);
                }
            }

            if (diary.getWorkType() == null || TextUtils.isEmpty(diary.getWorkType().getName()) || diary.getWorkType().getId() == ID_NONE) {
                holder.rowWorkType.setVisibility(View.GONE);
            } else {
                if (lineLeft > 0) {
                    holder.rowWorkType.setVisibility(View.VISIBLE);
                    holder.tvWorkType.setText(diary.getWorkType().getName());
                    holder.tvReadMore.setVisibility(lineLeft > 0 ? View.GONE : View.VISIBLE);
                    lineLeft -= 1;

                } else {
                    holder.rowWorkType.setVisibility(View.GONE);
                    holder.tvReadMore.setVisibility(lineLeft == 0 ? View.VISIBLE : View.GONE);
                }
            }

            if (TextUtils.isEmpty(diary.getDiseaseType()) || diary.getDiseaseType().equals(context.getString(R.string.post_none))) {
                holder.rowDisease.setVisibility(View.GONE);
            } else {
                if (lineLeft > 0) {
                    holder.rowDisease.setVisibility(View.VISIBLE);
                    holder.tvDisease.setText(diary.getDiseaseType());
                    holder.tvReadMore.setVisibility(lineLeft > 0 ? View.GONE : View.VISIBLE);
                    lineLeft -= 1;

                } else {
                    holder.rowDisease.setVisibility(View.GONE);
                    holder.tvReadMore.setVisibility(lineLeft == 0 ? View.VISIBLE : View.GONE);
                }
            }

            if (TextUtils.isEmpty(diary.getPesticideType()) || diary.getPesticideType().equals(context.getString(R.string.post_none))) {
                holder.rowPesticide.setVisibility(View.GONE);
            } else {
                if (lineLeft > 0) {
                    holder.rowPesticide.setVisibility(View.VISIBLE);
                    holder.tvPesticide.setText(diary.getPesticideType());
                    holder.tvReadMore.setVisibility(lineLeft > 0 ? View.GONE : View.VISIBLE);
                    lineLeft -= 1;

                } else {
                    holder.rowPesticide.setVisibility(View.GONE);
                    holder.tvReadMore.setVisibility(lineLeft == 0 ? View.VISIBLE : View.GONE);
                }
            }

            if (TextUtils.isEmpty(diary.getFertilizerType()) || diary.getFertilizerId().equals(String.valueOf(ID_NONE))) {
                holder.rowFertilizer.setVisibility(View.GONE);
            } else {
                if (lineLeft > 0) {
                    holder.rowFertilizer.setVisibility(View.VISIBLE);
                    String fertilizerString = "N" + diary.getNitrogen() + ", P" + diary.getPhosphoric() + ", K" + diary.getPotossium();
                    holder.tvFertilizer.setText(fertilizerString);
                    holder.tvReadMore.setVisibility(lineLeft > 0 ? View.GONE : View.VISIBLE);
                    lineLeft -= 1;

                } else {
                    holder.rowFertilizer.setVisibility(View.GONE);
                    holder.tvReadMore.setVisibility(lineLeft == 0 ? View.VISIBLE : View.GONE);
                }
            }

            if (TextUtils.isEmpty(diary.getIssuedDate())) {
                holder.rowDateSpecification.setVisibility(View.GONE);
            } else {
                if (lineLeft > 0) {
                    holder.rowDateSpecification.setVisibility(View.VISIBLE);
                    holder.tvReadMore.setVisibility(lineLeft > 0 ? View.GONE : View.VISIBLE);
                    holder.tvDateSpecification.setText(DateTimeNow.parseDateStringToLocalDateString(diary.getIssuedDate(),
                            DateTimeNow.yyyy_MM_dd_T_HH_mm_ss_SSSZ, context.getString(R.string.format_date_and_time)));
                } else {
                    holder.rowDateSpecification.setVisibility(View.GONE);
                    holder.tvReadMore.setVisibility(lineLeft == 0 ? View.VISIBLE : View.GONE);
                }
            }
            holder.tvReadMore.setVisibility(lineLeft <= 0 ? View.VISIBLE : View.GONE);

            holder.tvReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onReadMore(item);
                }
            });

        } else {
            Glide.with(context).load("").placeholder(R.drawable.ic_user_default)
                    .error(R.drawable.ic_user_default).into(holder.imgAvatar);
            //If Diary is null, image is invisible
            holder.linContainImages.setVisibility(View.GONE);
            holder.imgContent1.setVisibility(View.GONE);
            holder.layoutImage2.setVisibility(View.GONE);
            holder.layoutImage3.setVisibility(View.GONE);
            holder.imgContent31.setVisibility(View.GONE);
            holder.imgContent32.setVisibility(View.GONE);
            holder.imgContent33.setVisibility(View.GONE);
            holder.tableData.setVisibility(View.GONE);
        }

        //If this diary has field
        if (TextUtils.isEmpty(item.getFieldName())) {
            holder.layoutLocation.setVisibility(View.GONE);
            holder.tvField.setText("");
        } else {
            holder.tvField.setText(item.getFieldName());
            holder.layoutLocation.setVisibility(View.VISIBLE);
        }


        holder.tvTime.setText(DateTimeNow.compareTime(context, item.getDateTimeline()));
        holder.tvContent.setText(item.getDescription());

        //User post don't have Title
        holder.tvTitle.setVisibility(View.GONE);
        holder.btnMoveToNextStage.setVisibility(View.GONE);
    }

    private void configTimelineNotification(MyViewHolder holder, final int position, final TimelineResponse.ListTimeline item) {
        Glide.with(context).load(R.drawable.ic_timeline_notification).into(holder.imgAvatar);
        final TimelineResponse.Notification notification = item.getNotification();
        if (notification != null) {
            holder.tvTitle.setText(item.getTitle());
            holder.tvTitle.setVisibility(TextUtils.isEmpty(item.getTitle()) ? View.GONE : View.VISIBLE);

            try {
                AdviceDescriptionObject descriptionObject = new Gson().fromJson(item.getDescription(), AdviceDescriptionObject.class);
                holder.tvContent.setText(Html.fromHtml(descriptionObject.getContent()));
            } catch (Exception ex) {
                holder.tvContent.setText(Html.fromHtml(item.getSimpleDescription()));
            }

            if (notification.isMoveToNextStage()) {
                if (notification.getProcessToNextStage() == Utils.MoveToNextStage.NOT_PROCESS__LAST_STAGE ||
                        notification.getProcessToNextStage() == Utils.MoveToNextStage.NOT_PROCESS__NOT_LAST_STAGE) {
                    holder.btnMoveToNextStage.setVisibility(View.VISIBLE);
                    holder.btnMoveToNextStage.setText(notification.getProcessToNextStage() == Utils.MoveToNextStage.NOT_PROCESS__LAST_STAGE ?
                            R.string.move_to_next_stage : R.string.complete_stage);
                } else {
                    holder.btnMoveToNextStage.setVisibility(View.GONE);
                }
            } else {
                holder.btnMoveToNextStage.setVisibility(View.GONE);
            }

            holder.btnMoveToNextStage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onMoveToNextStage(position);
                }
            });
        }
    }

    private void configTimelineAdvice(MyViewHolder holder, final TimelineResponse.ListTimeline item) {
        Glide.with(context).load(R.drawable.ic_timeline_advice).into(holder.imgAvatar);
        final TimelineResponse.Advice advice = item.getAdvice();
        if (advice != null) {
            int lineLeft = LIMIT_LINE_READ_MORE;

            holder.tvTitle.setText(item.getTitle());
            if (!TextUtils.isEmpty(item.getTitle())) {
                holder.tvTitle.setVisibility(View.VISIBLE);
                lineLeft--;
            } else {
                holder.tvTitle.setVisibility(View.GONE);
            }
            holder.btnMoveToNextStage.setVisibility(View.GONE);

            final AdviceDescriptionObject descriptionObject = item.getDescriptionObject();
            if (descriptionObject != null) {
                holder.tvContent.setText(Html.fromHtml(descriptionObject.getContent()));
                int textLine = getLineCountOfTextViewBeforeRendering(descriptionObject.getContent());
                boolean isShowImage;

                if (!TextUtils.isEmpty(descriptionObject.getImages())) {
                    byte[] decodedString = Base64.decode(descriptionObject.getImages(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    holder.linContainImages.setVisibility(View.VISIBLE);
                    holder.imgContent1.setVisibility(View.VISIBLE);
                    holder.imgContent1.setImageBitmap(decodedByte);
                    holder.imgContent1.setOnClickListener(null);
                    isShowImage = true;
                } else {
                    holder.linContainImages.setVisibility(View.GONE);
                    holder.imgContent1.setVisibility(View.GONE);
                    isShowImage = false;
                }

                if (!TextUtils.isEmpty(descriptionObject.getUrl())) {
                    holder.linAdviceLink.setVisibility(View.VISIBLE);
                    holder.tvAdviceUrl.setText(descriptionObject.getUrl());
                    lineLeft--;

                    holder.tvAdviceUrl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(descriptionObject.getUrl()));
                            context.startActivity(browserIntent);
                        }
                    });
                }

                if (textLine > lineLeft || isShowImage) {
                    holder.tvReadMore.setVisibility(View.VISIBLE);
                } else {
                    holder.tvReadMore.setVisibility(View.GONE);
                }
            } else {
                holder.tvReadMore.setVisibility(View.GONE);
            }

            holder.tvReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentAdvice = new Intent(context, AdviceDetailActivity.class);
                    intentAdvice.putExtra(Utils.Name.ADVICE_ADVICE, advice);
                    intentAdvice.putExtra(Utils.Name.ADVICE_TITLE, item.getTitle());
                    App.descriptionObject = descriptionObject;
                    context.startActivity(intentAdvice);
                }
            });
        }
    }

    private void showDialogEditDiary(final String timelineId, final int position) {
        final MaterialDialog materialDialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_timeline, true)
                .cancelable(false)
                .build();
        materialDialog.show();

        TextView tvEdit = (TextView) materialDialog.findViewById(R.id.tvEdit);
        TextView tvDelete = (TextView) materialDialog.findViewById(R.id.tvDelete);
        TextView tvCancel = (TextView) materialDialog.findViewById(R.id.tvCancel);

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onDiaryEdit(getByIndex(position));
                materialDialog.dismiss();
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
                showDialogDelete(timelineId, position);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDialog.dismiss();
            }
        });

    }

    private void showDialogDelete(final String timelineId, final int position) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.delete_post).titleGravity(GravityEnum.CENTER)
                .content(R.string.diary_delete_confirm)
                .positiveText(R.string.btn_dialog_ok).positiveColorRes(R.color.bg_green_btn).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        deleteDiary(timelineId, position);
                    }
                })
                .neutralText(R.string.cancel).neutralColorRes(R.color.bg_green_btn).build();
        dialog.getActionButton(DialogAction.NEGATIVE).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        dialog.show();
    }

    private void deleteDiary(String timelineId, final int position) {
        APIManager.deleteDiary(timelineId, new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.code() == Utils.ErrorCode.SUCCESS_200) {
                    Toast.makeText(context, context.getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                    list.remove(getByIndex(position).getTimelineId());
                    notifyDataSetChanged();
                } else
                    Toast.makeText(context, context.getString(R.string.delete_fail), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(context, context.getString(R.string.delete_fail), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showImage(MyViewHolder holder, int position, ArrayList<String> listImageUrl) {
        if (listImageUrl.size() == 0) {
            //gone view which content image
            holder.linContainImages.setVisibility(View.GONE);
        } else {
            holder.linContainImages.setVisibility(View.VISIBLE);

            switch (listImageUrl.size()) {
                case 1:
                    holder.imgContent1.setVisibility(View.VISIBLE);
                    holder.layoutImage2.setVisibility(View.GONE);
                    holder.layoutImage3.setVisibility(View.GONE);
                    holder.imgContent31.setVisibility(View.GONE);
                    holder.imgContent32.setVisibility(View.GONE);
                    holder.imgContent33.setVisibility(View.GONE);
                    holder.relContent33.setVisibility(View.GONE);
                    //
                    Glide.with(context).load(listImageUrl.get(0)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent1);
                    holder.imgContent1.setLayoutParams(getLayoutParams(dp2px(250)));

                    onImageClick(holder.imgContent1, 0, getByIndex(position));

                    break;
                case 2:
                    holder.imgContent1.setVisibility(View.GONE);
                    holder.layoutImage2.setVisibility(View.VISIBLE);
                    holder.layoutImage3.setVisibility(View.GONE);
                    holder.imgContent31.setVisibility(View.GONE);
                    holder.imgContent32.setVisibility(View.GONE);
                    holder.imgContent33.setVisibility(View.GONE);
                    holder.relContent33.setVisibility(View.GONE);
                    //
                    Glide.with(context).load(listImageUrl.get(0)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent21);
                    Glide.with(context).load(listImageUrl.get(1)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent22);

                    onImageClick(holder.imgContent21, 0, getByIndex(position));
                    onImageClick(holder.imgContent22, 1, getByIndex(position));
                    break;
                case 3:
                    holder.imgContent1.setVisibility(View.VISIBLE);
                    holder.layoutImage2.setVisibility(View.GONE);
                    holder.layoutImage3.setVisibility(View.VISIBLE);
                    holder.imgContent31.setVisibility(View.VISIBLE);
                    holder.imgContent32.setVisibility(View.VISIBLE);
                    holder.imgContent33.setVisibility(View.GONE);
                    holder.relContent33.setVisibility(View.GONE);
                    //
                    Glide.with(context).load(listImageUrl.get(0)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent1);
                    holder.imgContent1.setLayoutParams(getLayoutParams(dp2px(150)));
                    holder.layoutImage3.setLayoutParams(getLayoutParams(dp2px(150)));

                    Glide.with(context).load(listImageUrl.get(1)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent31);
                    Glide.with(context).load(listImageUrl.get(2)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent32);

                    onImageClick(holder.imgContent1, 0, getByIndex(position));
                    onImageClick(holder.imgContent31, 1, getByIndex(position));
                    onImageClick(holder.imgContent32, 2, getByIndex(position));
                    break;
                case 4:
                    holder.imgContent1.setVisibility(View.VISIBLE);
                    holder.layoutImage2.setVisibility(View.GONE);
                    holder.layoutImage3.setVisibility(View.VISIBLE);
                    holder.imgContent31.setVisibility(View.VISIBLE);
                    holder.imgContent32.setVisibility(View.VISIBLE);
                    holder.imgContent33.setVisibility(View.VISIBLE);
                    holder.relContent33.setVisibility(View.VISIBLE);
                    holder.tvImageMore.setVisibility(View.GONE);
                    holder.viewImage33Black.setVisibility(View.GONE);
                    //
                    Glide.with(context).load(listImageUrl.get(0)).error(R.drawable.tomato_placeholder).into(holder.imgContent1);
                    holder.imgContent1.setLayoutParams(getLayoutParams(dp2px(150)));

                    Glide.with(context).load(listImageUrl.get(1)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent31);
                    Glide.with(context).load(listImageUrl.get(2)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent32);
                    Glide.with(context).load(listImageUrl.get(3)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent33);

                    onImageClick(holder.imgContent1, 0, getByIndex(position));
                    onImageClick(holder.imgContent31, 1, getByIndex(position));
                    onImageClick(holder.imgContent32, 2, getByIndex(position));
                    onImageClick(holder.imgContent33, 3, getByIndex(position));
                    break;
                default:
                    holder.imgContent1.setVisibility(View.GONE);
                    holder.layoutImage2.setVisibility(View.VISIBLE);
                    holder.layoutImage3.setVisibility(View.VISIBLE);
                    holder.imgContent31.setVisibility(View.VISIBLE);
                    holder.imgContent32.setVisibility(View.VISIBLE);
                    holder.imgContent33.setVisibility(View.VISIBLE);
                    holder.relContent33.setVisibility(View.VISIBLE);

                    if (listImageUrl.size() > 5) {
                        holder.tvImageMore.setVisibility(View.VISIBLE);
                        holder.viewImage33Black.setVisibility(View.VISIBLE);

                        String imageMore = "+" + (listImageUrl.size() - 5);
                        holder.tvImageMore.setText(imageMore);
                    } else {
                        holder.tvImageMore.setVisibility(View.GONE);
                        holder.viewImage33Black.setVisibility(View.GONE);
                    }

                    //
                    Glide.with(context).load(listImageUrl.get(0)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent21);
                    Glide.with(context).load(listImageUrl.get(1)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent22);
                    Glide.with(context).load(listImageUrl.get(2)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent31);
                    Glide.with(context).load(listImageUrl.get(3)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent32);
                    Glide.with(context).load(listImageUrl.get(4)).placeholder(R.drawable.image_placeholder).error(R.drawable.image_error).into(holder.imgContent33);

                    onImageClick(holder.imgContent21, 0, getByIndex(position));
                    onImageClick(holder.imgContent22, 1, getByIndex(position));
                    onImageClick(holder.imgContent31, 2, getByIndex(position));
                    onImageClick(holder.imgContent32, 3, getByIndex(position));
                    onImageClick(holder.imgContent33, 4, getByIndex(position));
                    break;
            }
        }
    }

    private void onImageClick(ImageView imageView, final int position, final TimelineResponse.ListTimeline timeline) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("ListTimeline", timeline);
                bundle.putInt("position", position);

                Intent intent = new Intent(context, TimelineViewImageActivity.class);
                intent.putExtra("TYPE", 1);//1 is Timeline, 2 is Post/Edit
                intent.putExtra("TimelineAdapter", bundle);
                context.startActivity(intent);
            }
        });
    }

    private LinearLayout.LayoutParams getLayoutParams(int height) {
        return new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
    }

    private int dp2px(int dps) {
        float scaleValue = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scaleValue + 0.5f);
    }

    public void onDestroy() {
        context = null;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvTime;
        private TextView tvField;
        private TextView tvContent;
        private TextView tvTitle;
        private TextView tvImageMore;
        private TextView tvTargetCrop;
        private TextView tvWorkType;
        private TextView tvDisease;
        private TextView tvPesticide;
        private TextView tvFertilizer;
        private TextView tvDateSpecification;
        private TextView tvReadMore;
        private TextView tvAdviceUrl;

        private TableLayout tableData;
        private TableRow rowTargetCrop;
        private TableRow rowWorkType;
        private TableRow rowDisease;
        private TableRow rowPesticide;
        private TableRow rowFertilizer;
        private TableRow rowDateSpecification;

        private ImageView imgAvatar;
        private ImageView imgToolbarInfo;
        private ImageView imgLocation;

        private ImageView imgContent1;
        private ImageView imgContent21;
        private ImageView imgContent22;
        private ImageView imgContent31;
        private ImageView imgContent32;
        private ImageView imgContent33;

        private Button btnMoveToNextStage;

        private LinearLayout layoutImage2;
        private LinearLayout layoutImage3;
        private LinearLayout linContainImages;
        private LinearLayout linAdviceLink;
        private RelativeLayout relContent33;
        private RelativeLayout layoutLocation;
        private View viewImage33Black;

        MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvTimelineName);
            tvTime = itemView.findViewById(R.id.tvTimelineTime);
            tvContent = itemView.findViewById(R.id.tvTimelineContent);
            tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemView.getResources().getDimension(R.dimen.tv_size_14sp));
            tvField = itemView.findViewById(R.id.tvTimelineField);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvImageMore = itemView.findViewById(R.id.tvImageMore);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTargetCrop = itemView.findViewById(R.id.tvTargetCrop);
            tvWorkType = itemView.findViewById(R.id.tvWorkType);
            tvDisease = itemView.findViewById(R.id.tvDisease);
            tvPesticide = itemView.findViewById(R.id.tvPesticide);
            tvFertilizer = itemView.findViewById(R.id.tvFertilizer);
            tvDateSpecification = itemView.findViewById(R.id.tvDateSpecification);
            tvReadMore = itemView.findViewById(R.id.tvReadMore);
            tvAdviceUrl = itemView.findViewById(R.id.tvAdviceUrl);

            tableData = itemView.findViewById(R.id.tableData);
            rowTargetCrop = itemView.findViewById(R.id.rowTargetCrop);
            rowWorkType = itemView.findViewById(R.id.rowWorkType);
            rowDisease = itemView.findViewById(R.id.rowDisease);
            rowPesticide = itemView.findViewById(R.id.rowPesticide);
            rowFertilizer = itemView.findViewById(R.id.rowFertilizer);
            rowDateSpecification = itemView.findViewById(R.id.rowDateSpecification);

            imgToolbarInfo = itemView.findViewById(R.id.imgToolbarInfo);
            imgLocation = itemView.findViewById(R.id.imgLocation);
            imgContent1 = itemView.findViewById(R.id.imgContent1);
            imgContent21 = itemView.findViewById(R.id.imgContent21);
            imgContent22 = itemView.findViewById(R.id.imgContent22);
            imgContent31 = itemView.findViewById(R.id.imgContent31);
            imgContent32 = itemView.findViewById(R.id.imgContent32);
            imgContent33 = itemView.findViewById(R.id.imgContent33);

            btnMoveToNextStage = itemView.findViewById(R.id.btnMoveToNextStage);

            layoutImage2 = itemView.findViewById(R.id.layoutImage2);
            layoutImage3 = itemView.findViewById(R.id.layoutImage3);
            linAdviceLink = itemView.findViewById(R.id.linAdviceLink);
            linContainImages = itemView.findViewById(R.id.linContainImages);
            relContent33 = itemView.findViewById(R.id.relContent33);
            viewImage33Black = itemView.findViewById(R.id.viewImage33Black);
            layoutLocation = itemView.findViewById(R.id.layoutLocation);
        }
    }
}
