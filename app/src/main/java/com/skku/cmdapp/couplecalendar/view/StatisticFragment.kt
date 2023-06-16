package com.skku.cmdapp.couplecalendar.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.skku.cmdapp.couplecalendar.R
import com.skku.cmdapp.couplecalendar.databinding.StatisticBinding


class StatisticFragment : Fragment() {
    //statistic xml바인딩 변수
    lateinit var statisticBinding:StatisticBinding

    // 파이차트 형식의 변수 생성
    lateinit var pieChart: PieChart
    //파이차트에 적용할 데이터 리스트 생성
    var pieChartDataList:ArrayList<PieChartData> = ArrayList()

    // 바 차트 형식의 변수 생성
    lateinit var barChart:BarChart
    //바차트에 적용할 데이터 리스트(월별 총 지출 리스트) 생성
    var monthlyTotalSpendingList:ArrayList<MonthlyTotalSpendingData> = ArrayList()


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        //바인딩 inflate
        statisticBinding=StatisticBinding.inflate(layoutInflater)

        // xml에서 그린 파이차트 뷰를 id로 불러와 파이차트 변수에 저장한다
        //pieChart =findViewById(R.id.statistic_pieChart)
        pieChart =statisticBinding.statisticPieChart

        //파이차트 데이터 초기화 파트
        initPieChartData()
        //파이차트 데이터 지출순 정렬 파트
        sortPieChartData()
        //파이 차트 생성 함수
        makePieChart(pieChart,pieChartDataList)

        //파이차트 아래 카테고리 별 지출 안내 리사이클러 뷰
        initPieChartRecyclerView()

        // xml에서 그린 막대차트 뷰를 id로 불러와 바차트 변수에 저장한다
        barChart =statisticBinding.statisticBarChart
        //파이차트 데이터 초기화 파트
        initBarChartData()
        makeBarChart(barChart,monthlyTotalSpendingList)
        return statisticBinding.root
    }



    //파이 차트에 들어갈 데이터 초기화 하는 함수
    fun initPieChartData(){
        pieChartDataList.add(PieChartData(R.drawable.food_icon,"식비",150000, getDrawable(requireContext(),R.drawable.round_back_pink_100),ContextCompat.getColor(requireContext(),R.color.icon_pink)))
        pieChartDataList.add(PieChartData(R.drawable.movie_icon,"문화생활",100000,getDrawable(requireContext(),R.drawable.round_back_green_100),ContextCompat.getColor(requireContext(),R.color.icon_green)))
        pieChartDataList.add(PieChartData(R.drawable.present_icon,"선물",70000,getDrawable(requireContext(),R.drawable.round_back_blue_100),ContextCompat.getColor(requireContext(),R.color.icon_blue)))
        pieChartDataList.add(PieChartData(R.drawable.list_icon,"기타",50000,getDrawable(requireContext(),R.drawable.round_back_yellow_100),ContextCompat.getColor(requireContext(),R.color.icon_yellow)))
        pieChartDataList.add(PieChartData(R.drawable.luggage_icon,"여행",250000,getDrawable(requireContext(),R.drawable.round_back_light_purple_100),ContextCompat.getColor(requireContext(),R.color.icon_purple)))
    }
    //위에서 초기화한 파이차트 데이터 리스트를 지출이 높은순으로 정렬하는 함수
    fun sortPieChartData(){
        pieChartDataList.sortByDescending { it.pieValue }
    }

    //파이차트 디자인, 데이터 적용 함수
    fun makePieChart(pieChart:PieChart, dataList:ArrayList<PieChartData>){

        //차트 내부에 원래 값이 아닌 백분율 값이 나오도록
        pieChart.setUsePercentValues(true)
        //차트 밑에 짧은 설명글 쓸건지
        pieChart.getDescription().setEnabled(false)
        //차트 외곽 상하좌우 여백
        pieChart.setExtraOffsets(5f, 5f, 5f, 5f)

        // on below line we are setting drag for our pie chart
        //pieChart.setDragDecelerationFrictionCoef(0.1f)


        //파이차트 내부에 원모양 빈공간 생기게 할 건지
        pieChart.setDrawHoleEnabled(true)
        //파이차트 내부에 원모양 빈공간 색깔
        pieChart.setHoleColor(Color.WHITE)
        //파이 차트 내부 원(빈공간)의 반지름
        pieChart.setHoleRadius(40f)
        //파이 차트 내부 원과 그 밖 원 사이의 회색 원의 반지름
        pieChart.setTransparentCircleRadius(50f)
        //차트 정중앙에 글자를 넣는것을 허용하는 속성
        pieChart.setDrawCenterText(true)
        //차트 정중앙에 넣을 글씨 입력
        pieChart.centerText="거지가\n꿈인가요?"

        //파이차트 내부 원의 테두리 선 두께
        //pieChart.setTransparentCircleAlpha(40)
        //파이차트 내부 원의 테두리 선 색깔 설정
        //pieChart.setTransparentCircleColor(Color.BLACK)


        //차트 조각 시작 위치
        //270f->12시, 0f->3시, 90f->6시, 180f->9시
        pieChart.setRotationAngle(270f)

        //시작 시 회전 애니메이션
        //뒤에 easing.속성 은 애니메이션 회전 속도의 강약 변화(ex천천히->빠르게)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        //사용자가 차트를 손가락으로 회전시킬 수 있는 속성
        pieChart.setRotationEnabled(false)
        //사용자가 조각을 손가락으로 누르면 조금 확대되는 속성
        pieChart.setHighlightPerTapEnabled(true)


        //legend=범례 -> 사용안함
        pieChart.legend.isEnabled = false

        //조각 레이블 텍스트 설정
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)


        //조각 레이블 중 %밑에 나오는 분류 이름(ex식비, 문화생활) 보이게 할 건지
        pieChart.setDrawEntryLabels(true)
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD)


        //차트에 넣을 데이터 리스트 생성 및 데이터 넣기 -> 여기는 나중에 매개변수로 받아오도록 수정해야함
        val entries: ArrayList<PieEntry> = ArrayList()
        //카테고리 순서에 맞는 색상 순서 담을 리스트
        val pieChartColorList=ArrayList<Int>()

        //생성된 데이터리스트에 실제 데이터 넣기
        for(data in dataList){
            entries.add(PieEntry(data.pieValue.toFloat(),data.pieCategory))
            //pieChartColorLIst에 따로 색상정보 담는 파트
            pieChartColorList.add(data.pieColor)
        }


        //위에서 만든 데이터 리스트를 pieDataSet형식으로 변환해 dataSet이라는 변수에 저장
        //해당 데이터 뭉치에 이름(레이블)도 븉여준다
        val dataSet = PieDataSet(entries, "spending_statistic")

        //아이콘 설정 여부 -> 뭔지 모름
        dataSet.setDrawIcons(false)


        //파이차트 색깔 지정
        dataSet.setColors(pieChartColorList)


        /*
        //조각 데이터 중 %숫자 옆으로 빼기 -> 꺾은선 생김
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE)
        //조각 데이터 중 이름 옆으로 빼기 -> 꺾은선 생김
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE)

        //2선으로 이루어진 꺾은 선 중 첫 번째 선의 길이
        dataSet.setValueLinePart1Length(0.6f)
        dataSet.setValueLinePart2Length(0.4f)
        dataSet.setValueLinePart1OffsetPercentage(80f)
        */

        //파이 조각 쪼개기
        //조각들 사이 빈 공간 크기
        dataSet.sliceSpace = 5f
        //이것도 뭔지 모름. 값 바꿔도 변하는 게 없음
        dataSet.iconsOffset = MPPointF(100f, 100f)
        //뭔지 모르지만 값이 커질수록 파이차트 전체 크기가 작아진다
        dataSet.selectionShift = 4f

        //숫자 뒤에 %기호 나오도록
        dataSet.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return String.format("%.1f%%", value)
            }
        }
        dataSet.valueTypeface= Typeface.DEFAULT_BOLD
        dataSet.valueTextSize=15f
        dataSet.valueTextColor=Color.WHITE

        //파이차트에 데이터 연결시키기
        pieChart.data=PieData(dataSet)

        //앞에서 highlightValues(0,0)->0번째 조각 강조 와 같이 강조효과를 넣었다면 이를 초기화 하는 매서드
        //pieChart.highlightValues(null)

        // 차트야 화면에 나와줘~
        pieChart.invalidate()

    }

    fun initPieChartRecyclerView(){
        statisticBinding.pieChartRecyclerView.layoutManager=LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
        statisticBinding.pieChartRecyclerView.adapter=PieChartDataAdapter(pieChartDataList)


    }
    fun initBarChartData() {
        monthlyTotalSpendingList.add(MonthlyTotalSpendingData(2022,9,200000))
        monthlyTotalSpendingList.add(MonthlyTotalSpendingData(2022,10,800000))
        monthlyTotalSpendingList.add(MonthlyTotalSpendingData(2022,11,400000))
        monthlyTotalSpendingList.add(MonthlyTotalSpendingData(2022,12,600000))
        monthlyTotalSpendingList.add(MonthlyTotalSpendingData(2023,1,500000))
        monthlyTotalSpendingList.add(MonthlyTotalSpendingData(2023,2,800000))
        monthlyTotalSpendingList.add(MonthlyTotalSpendingData(2023,3,300000))
        monthlyTotalSpendingList.add(MonthlyTotalSpendingData(2023,4,620000))


    }

    fun makeBarChart(barChart: BarChart,dataList:ArrayList<MonthlyTotalSpendingData>){
        barChart.setDrawGridBackground(false)
        barChart.setDrawBarShadow(false)
        barChart.setDrawBorders(false)

        //차트 밑에 짧은 설명글 쓸건지
        barChart.getDescription().setEnabled(false)
        //X, Y 바의 애니메이션 효과
        barChart.animateY(1000)
        barChart.animateX(1000)


        //범례 설정
        barChart.legend.isEnabled = false
        //차트 줌 가능 여부 설정
        barChart.setScaleEnabled(false)

        // 바텀 좌표 값
        val xAxis: XAxis = barChart.xAxis
        // x축 위치 설정
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        // x축 레이블 표시 간격 2f->2의 배수번째만 표시, 3f->3의 배수번째만 표시
        xAxis.granularity = 1f
        // x축 텍스트 컬러 설정
        // x축 선 설정 (default = true)
        xAxis.setDrawAxisLine(false)
        // 격자선 설정 (default = true)
        xAxis.setDrawGridLines(false)

        val leftAxis: YAxis = barChart.axisLeft
        // 좌측 선 설정 (default = true)
        leftAxis.setDrawAxisLine(false)

        val rightAxis: YAxis = barChart.axisRight
        // 우측 선 설정 (default = true)
        //rightAxis.setDrawAxisLine(false)
        rightAxis.setDrawLabels(false)



        //차트에 넣을 데이터 리스트 생성 및 데이터 넣기 -> 여기는 나중에 매개변수로 받아오도록 수정해야함
        val entries: ArrayList<BarEntry> = ArrayList()
        //생성된 데이터리스트에 실제 데이터 넣기
        for(i in 0..5){
            entries.add(BarEntry((i+1).toFloat(),dataList[dataList.size-(6-i)].totalSpending.toFloat()))
        }

        //위에서 만든 데이터 리스트를 pieDataSet형식으로 변환해 dataSet이라는 변수에 저장
        //해당 데이터 뭉치에 이름(레이블)도 븉여준다

        val dataSet = BarDataSet(entries, "monthly_spending")


        //막대 색깔 담을 리스트
        val barChartColorList= mutableListOf<Int>()
        for(i in 0 until entries.size){
            //마지막(이번 달)만 분홍색
            if(i==5){
                barChartColorList.add(ContextCompat.getColor(requireContext(),R.color.icon_pink))
            }
            //나머지 달들은 회색
            else{
                barChartColorList.add(ContextCompat.getColor(requireContext(),R.color.gray))
            }
        }
        dataSet.setColors(barChartColorList)

    /*
        dataSet.valueTypeface= Typeface.DEFAULT_BOLD
        dataSet.valueTextSize=15f
        dataSet.valueTextColor=Color.WHITE
    */

        //파이차트에 데이터 연결시키기
        //barChart.data=BarData(dataSet)
        val data = BarData(dataSet)
        data.barWidth=0.5f

        //막대 그래프 위에 값표시 안 되게
        data.setDrawValues(false)
        /* 표시할 거면 아래 값 사용
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
         */
        barChart.setData(data)

        // 차트야 화면에 나와줘~
        barChart.invalidate()

    }
}