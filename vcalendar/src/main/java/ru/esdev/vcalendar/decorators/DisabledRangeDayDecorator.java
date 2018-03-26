package ru.esdev.vcalendar.decorators;

import android.content.Context;
import android.support.annotation.IntDef;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

import ru.esdev.vcalendar.DayDecorator;
import ru.esdev.vcalendar.R;
import ru.esdev.vcalendar.models.CalendarDay;

import static ru.esdev.vcalendar.decorators.DisabledRangeDayDecorator.RangeMode.AFTER;
import static ru.esdev.vcalendar.decorators.DisabledRangeDayDecorator.RangeMode.BEFORE;

/**
 * vcalendarapp. 2018
 *
 * @author Eduard Maximovich <edward.vstock@gmail.com>
 */
public class DisabledRangeDayDecorator implements DayDecorator {
    private final WeakReference<Context> mContext;
    private final DateTime mInitial;
    @RangeMode
    private int mMode;

    public DisabledRangeDayDecorator(Context context, @RangeMode int mode, DateTime initial) {
        mContext = new WeakReference<>(context);
        mMode = mode;
        if(mode == BEFORE) {
            mInitial = initial.minusDays(1);
        } else {
            mInitial = initial.plusDays(1);
        }
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean shouldDecorate(CalendarDay calendarDay) {
        DateTime cur = calendarDay.getDateTime();
        if(mMode == BEFORE && cur.compareTo(mInitial) < 0) {
            return true;
        } else if(mMode == AFTER && cur.compareTo(mInitial) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void decorate(CalendarDay calendarDay, TextView dayView) {
        dayView.setTextColor(mContext.get().getResources().getColor(R.color.vcalendarTextColorHalf));
    }

    @IntDef({
            BEFORE,
            AFTER
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface RangeMode {
        int BEFORE = 0;
        int AFTER = 1;
    }
}
