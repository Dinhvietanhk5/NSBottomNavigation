package com.newsoft.nsbottomnavigation.notification;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author repitch
 */
public class NSNotification implements Parcelable {

    @Nullable
    private String text; // can be null, so notification will not be shown

    @ColorInt
    private int textColor; // if 0 then use default value

    @ColorInt
    private int backgroundColor; // if 0 then use default value

    public NSNotification() {
        // empty
    }

    private NSNotification(Parcel in) {
        text = in.readString();
        textColor = in.readInt();
        backgroundColor = in.readInt();
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(text);
    }

    public String getText() {
        return text;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public static NSNotification justText(String text) {
        return new Builder().setText(text).build();
    }

    public static List<NSNotification> generateEmptyList(int size) {
        List<NSNotification> notificationList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            notificationList.add(new NSNotification());
        }
        return notificationList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeInt(textColor);
        dest.writeInt(backgroundColor);
    }

    public static class Builder {
        @Nullable
        private String text;
        @ColorInt
        private int textColor;
        @ColorInt
        private int backgroundColor;

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setTextColor(@ColorInt int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder setBackgroundColor(@ColorInt int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public NSNotification build() {
            NSNotification notification = new NSNotification();
            notification.text = text;
            notification.textColor = textColor;
            notification.backgroundColor = backgroundColor;
            return notification;
        }
    }

    public static final Creator<NSNotification> CREATOR = new Creator<NSNotification>() {
        @Override
        public NSNotification createFromParcel(Parcel in) {
            return new NSNotification(in);
        }

        @Override
        public NSNotification[] newArray(int size) {
            return new NSNotification[size];
        }
    };

}
