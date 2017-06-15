define(function() {
var theme = {
    // Ĭ��ɫ��
    color: [
        '#1790cf','#1bb2d8','#99d2dd','#88b0bb',
        '#1c7099','#038cc4','#75abd0','#afd6dd'
    ],

    // ͼ�����
    title: {
        textStyle: {
            fontWeight: 'normal',
            color: '#1790cf'
        }
    },
    
    // ֵ��
    dataRange: {
        color:['#1178ad','#72bbd0']
    },

    // ������
    toolbox: {
        color : ['#1790cf','#1790cf','#1790cf','#1790cf']
    },

    // ��ʾ��
    tooltip: {
        backgroundColor: 'rgba(0,0,0,0.5)',
        axisPointer : {            // ������ָʾ���������ᴥ����Ч
            type : 'line',         // Ĭ��Ϊֱ�ߣ���ѡΪ��'line' | 'shadow'
            lineStyle : {          // ֱ��ָʾ����ʽ����
                color: '#1790cf',
                type: 'dashed'
            },
            crossStyle: {
                color: '#1790cf'
            },
            shadowStyle : {                     // ��Ӱָʾ����ʽ����
                color: 'rgba(200,200,200,0.3)'
            }
        }
    },

    // �������ſ�����
    dataZoom: {
        dataBackgroundColor: '#eee',            // ���ݱ�����ɫ
        fillerColor: 'rgba(144,197,237,0.2)',   // �����ɫ
        handleColor: '#1790cf'     // �ֱ���ɫ
    },
    
    // ����
    grid: {
        borderWidth: 0
    },

    // ��Ŀ��
    categoryAxis: {
        axisLine: {            // ��������
            lineStyle: {       // ����lineStyle����������ʽ
                color: '#1790cf'
            }
        },
        splitLine: {           // �ָ���
            lineStyle: {       // ����lineStyle�����lineStyle������������ʽ
                color: ['#eee']
            }
        }
    },

    // ��ֵ��������Ĭ�ϲ���
    valueAxis: {
        axisLine: {            // ��������
            lineStyle: {       // ����lineStyle����������ʽ
                color: '#1790cf'
            }
        },
        splitArea : {
            show : true,
            areaStyle : {
                color: ['rgba(250,250,250,0.1)','rgba(200,200,200,0.1)']
            }
        },
        splitLine: {           // �ָ���
            lineStyle: {       // ����lineStyle�����lineStyle������������ʽ
                color: ['#eee']
            }
        }
    },

    timeline : {
        lineStyle : {
            color : '#1790cf'
        },
        controlStyle : {
            normal : { color : '#1790cf'},
            emphasis : { color : '#1790cf'}
        }
    },

    // K��ͼĬ�ϲ���
    k: {
        itemStyle: {
            normal: {
                color: '#1bb2d8',          // ���������ɫ
                color0: '#99d2dd',      // ���������ɫ
                lineStyle: {
                    width: 1,
                    color: '#1c7099',   // ���߱߿���ɫ
                    color0: '#88b0bb'   // ���߱߿���ɫ
                }
            }
        }
    },
    
    map: {
        itemStyle: {
            normal: {
                areaStyle: {
                    color: '#ddd'
                },
                label: {
                    textStyle: {
                        color: '#c12e34'
                    }
                }
            },
            emphasis: {                 // Ҳ��ѡ����ʽ
                areaStyle: {
                    color: '#99d2dd'
                },
                label: {
                    textStyle: {
                        color: '#c12e34'
                    }
                }
            }
        }
    },
    
    force : {
        itemStyle: {
            normal: {
                linkStyle : {
                    color : '#1790cf'
                }
            }
        }
    },
    
    chord : {
        padding : 4,
        itemStyle : {
            normal : {
                borderWidth: 1,
                borderColor: 'rgba(128, 128, 128, 0.5)',
                chordStyle : {
                    lineStyle : {
                        color : 'rgba(128, 128, 128, 0.5)'
                    }
                }
            },
            emphasis : {
                borderWidth: 1,
                borderColor: 'rgba(128, 128, 128, 0.5)',
                chordStyle : {
                    lineStyle : {
                        color : 'rgba(128, 128, 128, 0.5)'
                    }
                }
            }
        }
    },
    
    gauge : {
        axisLine: {            // ��������
            show: true,        // Ĭ����ʾ������show������ʾ���
            lineStyle: {       // ����lineStyle����������ʽ
                color: [[0.2, '#1bb2d8'],[0.8, '#1790cf'],[1, '#1c7099']], 
                width: 8
            }
        },
        axisTick: {            // ������С���
            splitNumber: 10,   // ÿ��splitϸ�ֶ��ٶ�
            length :12,        // ����length�����߳�
            lineStyle: {       // ����lineStyle����������ʽ
                color: 'auto'
            }
        },
        axisLabel: {           // �������ı���ǩ�����axis.axisLabel
            textStyle: {       // ��������Ĭ��ʹ��ȫ���ı���ʽ�����TEXTSTYLE
                color: 'auto'
            }
        },
        splitLine: {           // �ָ���
            length : 18,         // ����length�����߳�
            lineStyle: {       // ����lineStyle�����lineStyle������������ʽ
                color: 'auto'
            }
        },
        pointer : {
            length : '90%',
            color : 'auto'
        },
        title : {
            textStyle: {       // ��������Ĭ��ʹ��ȫ���ı���ʽ�����TEXTSTYLE
                color: '#333'
            }
        },
        detail : {
            textStyle: {       // ��������Ĭ��ʹ��ȫ���ı���ʽ�����TEXTSTYLE
                color: 'auto'
            }
        }
    },
    
    textStyle: {
        fontFamily: '΢���ź�, Arial, Verdana, sans-serif'
    }
};

    return theme;
});