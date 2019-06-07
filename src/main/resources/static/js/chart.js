function drawChart(chart, id) {
    var testData = [];
    // testData.add();
    // console.log(chart_1.xAxisCategories);
    var chart = Highcharts.chart(id, {

        title: {
            text: chart.title
        },

        subtitle: {
            text: 'Source: thesolarfoundation.com'
        },

        yAxis: {
            title: {
                text: chart.yaxisText
            }
        },
        xAxis: {
            categories: chart.xaxisCategories
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
            name: chart.series[0].name,
            data: chart.series[0].data //350, 452, 683, 601, 598, 535, 340, 340
        }, {
            name: chart.series[1].name,
            data: chart.series[1].data //673, 820, 1156, 1024, 1055, 998, 832, 720
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