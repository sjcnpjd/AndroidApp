package com.cookandroid.food

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils.replace
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import com.cookandroid.food.databinding.FragCalendarBinding
import com.cookandroid.food.databinding.FragMainBinding
import com.cookandroid.food.vm.MyViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*

class CalendarFragment : Fragment(R.layout.frag_calendar) {
    private val viewmodel by activityViewModels<MyViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragCalendarBinding.bind(view)

        var startTimeCalendar = Calendar.getInstance()
        var endTimeCalendar = Calendar.getInstance()

        val currentYear = startTimeCalendar.get(Calendar.YEAR)
        val currentMonth = startTimeCalendar.get(Calendar.MONTH)
        val currentDate = startTimeCalendar.get(Calendar.DATE)



        endTimeCalendar.set(Calendar.MONTH, currentMonth + 3)

        binding.materialCalendar.state().edit()
            .setFirstDayOfWeek(Calendar.SUNDAY)
            .setMinimumDate(CalendarDay.from(currentYear, currentMonth - 3, 1))
            .setMaximumDate(
                CalendarDay.from(
                    currentYear,
                    currentMonth + 3,
                    endTimeCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                )
            )
            .setCalendarDisplayMode(CalendarMode.MONTHS)
            .commit()

        val stCalendarDay = CalendarDay(currentYear, currentMonth, currentDate)
        val enCalendarDay = CalendarDay(
            endTimeCalendar.get(Calendar.YEAR),
            endTimeCalendar.get(Calendar.MONTH),
            endTimeCalendar.get(Calendar.DATE)
        )

        val sundayDecorator = SundayDecorator()
        val saturdayDecorator = SaturdayDecorator()
        val minMaxDecorator = MinMaxDecorator(stCalendarDay, enCalendarDay)
//        val boldDecorator = BoldDecorator(stCalendarDay, enCalendarDay)
        val todayDecorator = TodayDecorator(requireContext())


        binding.materialCalendar.addDecorators(
            sundayDecorator,
            saturdayDecorator,
//            boldDecorator,
            minMaxDecorator
        )


        binding.materialCalendar.setOnDateChangedListener { widget, date, selected ->
            val calList = ArrayList<CalendarDay>()
            calList.add(date)



            val datefragment : DateFragment = DateFragment()
            // 매니저 필요
            val fragmentManager : FragmentManager = (activity as FragmentActivity).supportFragmentManager
            //트랙잭션 생성 -> 작업의 단위 ( 시작과 끝 )
            setFragmentResult("dateKey1", bundleOf("bundleKey1" to date.year.toString()))
            setFragmentResult("dateKey2", bundleOf("bundleKey2" to date.month.toString()))
            setFragmentResult("dateKey3", bundleOf("bundleKey3" to date.day.toString()))

            val fragmentTransaction = fragmentManager.beginTransaction() // 시작
            fragmentTransaction.replace(R.id.main_frame, datefragment) // 뷰아이디 자리에 fragment가 간다.
            fragmentTransaction.commit()

            val wholeDateY = date.year
            val wholeDateM = date.month+1
            val wholeDateD = date.day
            val wholeDate = "$wholeDateY-$wholeDateM-$wholeDateD"



            viewmodel.date = wholeDate
            Log.d("kj",  viewmodel.date)

            viewmodel.sendDateRequest(MainActivity.ApplicationContext())


        }

    }

    class TodayDecorator(context: Context): DayViewDecorator {
        private var date = CalendarDay.today()
        val drawable = context.resources.getDrawable(R.drawable.radius)
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return day?.equals(date)!!
        }
        override fun decorate(view: DayViewFacade?) {
            view?.setBackgroundDrawable(drawable)
        }
    }

    class SundayDecorator : DayViewDecorator {
        private val calendar = Calendar.getInstance()
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            day?.copyTo(calendar)
            val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
            return weekDay == Calendar.SUNDAY
        }

        override fun decorate(view: DayViewFacade?) {
            view?.addSpan(object : ForegroundColorSpan(Color.RED) {})
        }
    }

    class SaturdayDecorator : DayViewDecorator {
        private val calendar = Calendar.getInstance()
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            day?.copyTo(calendar)
            val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
            return weekDay == Calendar.SATURDAY
        }

        override fun decorate(view: DayViewFacade?) {
            view?.addSpan(object : ForegroundColorSpan(Color.BLUE) {})
        }
    }

    class MinMaxDecorator(min: CalendarDay, max: CalendarDay) : DayViewDecorator {
        val maxDay = max
        val minDay = min
        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return (day?.month == maxDay.month && day.day > maxDay.day)
                    || (day?.month == minDay.month && day.day < minDay.day)
        }

        override fun decorate(view: DayViewFacade?) {
//            view?.addSpan(object : ForegroundColorSpan(Color.parseColor("#d2d2d2")) {})
        }
    }



}