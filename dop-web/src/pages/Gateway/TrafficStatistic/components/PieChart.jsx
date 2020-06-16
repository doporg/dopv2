import React from 'react';
import ReactDOM from 'react-dom';
var echarts = require('echarts/lib/echarts');
require('echarts/lib/chart/pie');
require('echarts/lib/component/title');

export default class Pie extends React.Component {

    constructor(props) {
        super(props);
        Pie.setPieOption = Pie.setPieOption.bind(this);
        this.initPieChart = this.initPieChart.bind(this)
    }

    initPieChart() {
        const { data } = this.props;
        let myChart = echarts.init(this.refs.pieChart);
        let options = Pie.setPieOption(data);
        myChart.setOption(options)
    }

    componentDidMount() {
        this.initPieChart()
    }

    componentDidUpdate() {
        this.initPieChart()
    }

    render() {
        return (
            <div className="pie-react">
                <div ref="pieChart" style={{width: "550%", height: "250%"}}>
                    ""
                </div>
            </div>
        )
    }

    static setPieOption(data) {
        return {
            series : [
                {
                    name: '比例',
                    type: 'pie',
                    data: data,
                    label: {
                        normal: {
                            formatter: "{d}% \n{b}",
                        }
                    }
                }
            ]
        }
    }
}
