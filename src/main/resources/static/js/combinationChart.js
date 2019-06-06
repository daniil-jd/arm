function drawCombinationChart() {
    var testdata = [
        ["", "Amount", "GP", "Quantity", "Fourth"],
        ["2018-12-12", 160, 40, 4, 80],
        ["2018-12-13", 500, 450, 6, 80],
        ["2018-12-14", 300, 250, 5, 80],
        ["2018-12-15", 300, 250, 5, 85],
        ["2018-12-16", 300, 250, 5, 75]
    ];
    var options = {
        chart: {
            type: 'column',  //'line',
            zoomType: 'xy'
        },
        title: {
            text: 'Testing'
        },

        xAxis: {
            title: {
                text: 'Days'
            }
        },
        yAxis: [
            {
                title: {
                    text: 'USD'
                }
            }
        ],
        noData: {
            style: {
                fontWeight: 'bold',
                fontSize: '15px',
                color: '#303030'
            }
        },
        series: [{}]
    };

    // Highcharts.ajax({
    //     function(testdata) {
    //         options.series[0].data = testdata;
    //         Highcharts.chart('container', options);
    //     }
    // });


    var chartComb = Highcharts.chart('combination', options);
    console.log(testdata);
    chartComb.series.push(testdata);
    // console.log(chartComb.series[0].data)
}