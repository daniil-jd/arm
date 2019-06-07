function drawCombinationChart (json) {
    var jsontest = [{
        "data": ["2018-12-12", "2018-12-13", "2018-12-14", "2018-12-15", "2018-12-16"]
    }, {
        "name": "det1",
        "data": [10, 20, 30, 40]
    }, {
        "name": "det2",
        "data": [11, 22, 33, 44]
    }, {
        "name": "det3",
        "data": [12, 23, 34, 45]
    }, {
        "name": "det4",
        "data": [98.9663, 99.3663]
    }, {
        "name": "det5",
        "data": [104.97, 91.4251]
    }];
    console.log(json);
    var len = json.length;
    console.log(len);
    i = 0;

    var options = {
        chart: {
            type: 'column',  //'line',
            zoomType: 'xy'
        },
        title: {
            text: 'Количество обнаружений по детекторам'
        },
        xAxis: {
            title: {
                text: 'Дни'
            },
            categories: []
        },
        yAxis: [
            {
                title: {
                    text: 'Обнаружения'
                }
            }
        ],
        series: []
    };

    for (i; i < len; i++) {
        if (i === 0) {
            var dat = json[i].data,
                lenJ = dat.length,
                j = 0,
                tmp;

            for (j; j < lenJ; j++) {
                tmp = dat[j].split(';');
                options.xAxis.categories.push(tmp[0]);
            }
        } else {
            options.series.push(json[i]);
        }
    }

    Highcharts.chart('combination_chart', options);

};