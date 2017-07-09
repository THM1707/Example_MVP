package com.nothing.example_mvp.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by THM on 7/7/2017.
 */
public class TaskContract {
    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }
}
