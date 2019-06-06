function drawChart(chart_1) {
    var chart = Highcharts.chart('chart', {

        title: {
            text: chart_1.title
        },

        subtitle: {
            text: 'Source: thesolarfoundation.com'
        },

        yAxis: {
            title: {
                text: chart_1.yAxisText
            }
        },
        xAxis: {
            categories: chart_1.xAxisCategories
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },

        plotOptions: {
            series: {
                label: {
                    connectorAllowed: false
                }
            }
        },

        series: [{
            name: chart_1.series1.name,
            data: chart_1.series1.data //350, 452, 683, 601, 598, 535, 340, 340
        }, {
            name: chart_1.series2.name,
            data: chart_1.series2.data //673, 820, 1156, 1024, 1055, 998, 832, 720
        }],

        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                chartOptions: {
                    legend: {
                        layout: 'horizontal',
                        align: 'center',
                        verticalAlign: 'bottom'
                    }
                }
            }]
        }
    });
}