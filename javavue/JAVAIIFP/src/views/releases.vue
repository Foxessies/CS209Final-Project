<!--界面1-->
<template>
  <div class="fundlist">
    <div class="title">
      <el-row :gutter="100">
        <el-col :span="13">
          <div class="data">
            <el-statistic group-separator="," :precision="2" :value="value1" :title="title1"></el-statistic>
          </div>
        </el-col>
      </el-row>
    </div>

    <div class="title">
      <el-statistic group-separator="," :precision="2"  :title='"[[ Commits between two releases ]]"'></el-statistic>
    </div>

    <div class="table_container" style="margin-left: 20px;">
      <el-table v-if="tableData.length > 0" :data="tableData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
                min-height="460px" border style="width: 100%" height="240">

        <el-table-column label="Release" align='center' width="570">
          <template slot-scope="scope">
            <span>{{ scope.row.id}}</span>
          </template>
        </el-table-column>
        <el-table-column label="Commits number" align="center" width="570">
          <template slot-scope="scope">
            <span>{{ scope.row.type}}</span>
          </template>
        </el-table-column>
      </el-table>
      <div v-else class="txt">{{message}}</div>
    </div>

    <div class="title">
      <el-statistic group-separator="," :precision="2"  :title='"[[ Commits Time 分布 ]]"'></el-statistic>
    </div>

    <div class="chart">
      <el-row :gutter="100">
        <div>
          <div class="echart1" id="mychart1" :style="myChartStyle0"></div>
        </div>
        <div>
          <div class="echart2" id="mychart2" :style="myChartStyle0"></div>
        </div>
        <el-col :span="20">
          <div></div>
        </el-col>
      </el-row>
    </div>
    <div>
      <div class="echart3" id="mychart3" :style="myChartStyle"></div>
    </div>


  </div>
</template>

<script>
import HistoryDialong from '../components/HistoryDialong.vue'
import * as echarts from "echarts";
import "echarts-wordcloud/dist/echarts-wordcloud";
import "echarts-wordcloud/dist/echarts-wordcloud.min";
export default {
  name: 'FundList',
  data () {
    return {
      xData: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"], //横坐标
      yData: [ ], //数据
      quarter:[ ],
      day:[ ],
      myChartStyle0: { float: "left", width: "40%", height: "400px" } ,//图表样式
      myChartStyle: { float: "left", width: "100%", height: "400px" } ,//图表样式

      like: true,
      value1: 0,
      title1: "Total Releases",

      input: "Hello Element UI!",

      visible: false,
      message: '数据不存在',
      search_data: {
        startDate: '',
        endDate: ''
      },

      currentPage0: 1, // 初始页
      pagesize0: 55,

      currentPage: 1, // 初始页
      pagesize: 55,
      tableData0:[
      ],
      tableData: [{
        // 'id': '1',
        // 'type': 2,
      },
      ], // 分页数据
    }
  },
  created () {
    const _this=this
    this.$axios.get('http://localhost:8080/releases').then((resp)=>{
      _this.tableData0=resp.data[1]
      for(let x=0;x<54;x++){
        _this.tableData[x]= {
          'id':(x).toString()+'-'+(x+1).toString(),
          'type':_this.tableData0[x]
        }
        if(x===0){
          _this.tableData[x]= {
            'id':'before NO.1 release',
            'type':_this.tableData0[53-x]
          }
        }
        if(x===53){
          _this.tableData[x]= {
            'id':'after NO.53 release',
            'type':_this.tableData0[53-x]
          }
        }
      }
      console.log(_this.tableData[2].id)

      _this.value1=resp.data[0]
      _this.yData=resp.data[3]
      console.log(_this.yData)
      _this.quarter=resp.data[2]
      _this.day=resp.data[4]

      console.log(_this.initEcharts().data)
    })
  },

  mounted() {
    this.initEcharts();
  },
  methods: {
    handleSizeChange: function (size) {
      this.pagesize = size
      console.log(this.pagesize) // 每页下拉显示数据
    },
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
      console.log(this.currentPage) // 点击第几页
    },

    initEcharts() {
      // 基本柱状图
      const option1 = {
        xAxis: {
          data: ["spring","summer","autumn","winter"]
        },
        yAxis: {},
        series: [
          {
            barWidth: "30%", // 每个柱条的宽度就是类目宽度的 20%
            type: "bar", //形状为柱状图
            // data: this.yData
            data: this.quarter,

            itemStyle: {
              normal: {
                color: "darkred",
                shadowColor: "darkred",
                opacity: 0.5,
                barBorderRadius: 5,
                borderWidth: 1,
                label: {
                  show: true, //开启显示数值
                  position: 'top', //数值在上方显示
                  textStyle: {  //数值样式
                    color: 'darkred',   //字体颜色
                    fontSize: 14  //字体大小
                  }
                }
              }
            },
          }
        ]
      };

      const myChart1 = echarts.init(document.getElementById("mychart1"));
      myChart1.setOption(option1);
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        myChart1.resize();
      });

      const option2 = {
        xAxis: {
          data: ["morning","noon","afternoon","night","midnight"]
        },
        yAxis: {},
        series: [
          {
            barWidth: "30%", // 每个柱条的宽度就是类目宽度的 20%
            type: "bar", //形状为柱状图
            // data: this.yData
            data: this.day,

            itemStyle: {
              normal: {
                color: "#7795B9",
                shadowColor: "#7795B9",
                opacity: 0.5,
                barBorderRadius: 5,
                borderWidth: 1,
                label: {
                  show: true, //开启显示数值
                  position: 'top', //数值在上方显示
                  textStyle: {  //数值样式
                    color: '#7795B9',   //字体颜色
                    fontSize: 14  //字体大小
                  }
                }
              }
            },
          }
        ]
      };

      const myChart2 = echarts.init(document.getElementById("mychart2"));
      myChart2.setOption(option2);
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        myChart2.resize();
      });

      const option3 = {
        xAxis: {
          data: this.xData
        },
        yAxis: {},
        series: [
          {
            barWidth: "30%", // 每个柱条的宽度就是类目宽度的 20%
            type: "bar", //形状为柱状图
            // data: this.yData
            data: this.yData,

            itemStyle: {
              normal: {
                color: "black",
                shadowColor: "black",
                opacity: 0.5,
                barBorderRadius: 5,
                borderWidth: 1,
                label: {
                  show: true, //开启显示数值
                  position: 'top', //数值在上方显示
                  textStyle: {  //数值样式
                    color: 'black',   //字体颜色
                    fontSize: 14  //字体大小
                  }
                }
              }
            },
          }
        ]
      };

      const myChart3 = echarts.init(document.getElementById("mychart3"));
      myChart3.setOption(option3);
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        myChart3.resize();
      });
    }
  },

  components: {
    HistoryDialong
  }
}
</script>
<style scoped>
.data{

}

.elst{

}

.title{
  border-top: 15px solid white;
}

.table_container{
  border-top: 20px solid white;
  border-bottom: 30px solid white;
}

.fundlist {
  padding: 10px;
}

.btnleft {
  float: left;
  margin-left: 20px;
}

.paginations0 {
  text-align: right;
  margin-top: 10px;
}

.paginations {
  text-align: right;
  margin-top: 10px;
}

.txt0 {
  padding: 20px 0;
  background: #eee;
  font-size: 16px;
  text-align: center;
  color: #606266;
}
.txt {
  padding: 20px 0;
  background: #eee;
  font-size: 16px;
  text-align: center;
  color: #606266;
}
.add {
  color: white;
}
.echart1{
  border-left: 130px solid white;
}

.echart3{
  border-bottom: 100px solid white;
}
</style>
