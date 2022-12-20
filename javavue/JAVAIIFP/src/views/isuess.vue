<!--界面0内-->
<template>
	<div class="fundlist">
    <div class="title0">
      <el-row :gutter="25">
        <el-col :span="4">
          <div class="els1">
            <el-statistic group-separator="," :precision="2" :value="value1" :title="title1"></el-statistic>
          </div>
        </el-col>
        <el-col :span="4">
          <div>
            <el-statistic group-separator="," :precision="2" :value="value2" :title="title2"></el-statistic>
          </div>
        </el-col>
        <el-col :span="4">
          <div>
            <el-statistic group-separator="," :precision="2" :value="value3" :title="title3"></el-statistic>
          </div>
        </el-col>
        <el-col :span="4">
          <div>
            <el-statistic group-separator="," :precision="2" :value="value4" :title="title4"></el-statistic>
          </div>
        </el-col>
        <el-col :span="4">
          <div>
            <el-statistic group-separator="," :precision="2" :value="value5" :title="title5"></el-statistic>
          </div>
        </el-col>

      </el-row>
    </div>

<!--		<div class="table_container2" style="margin-left: 20px;">-->
<!--			<el-table v-if="tableData.length > 0" :data="tableData.slice((currentPage-1)*pagesize,currentPage*pagesize)"-->
<!--				min-height="460px" border style="width: 100%">-->

<!--				<el-table-column label="Issues" align='center' width="600">-->
<!--					<template slot-scope="scope">-->
<!--						<span>{{ scope.row.id}}</span>-->
<!--					</template>-->
<!--				</el-table-column>-->
<!--				<el-table-column label="Condition" align="center" width="300">-->
<!--					<template slot-scope="scope">-->
<!--						<span>{{ scope.row.type}}</span>-->
<!--					</template>-->
<!--				</el-table-column>-->
<!--				<el-table-column label="Solving time" align="center" width="300">-->
<!--					<template slot-scope="scope">-->
<!--						<span>{{ scope.row.quanxian}}</span>-->
<!--					</template>-->
<!--				</el-table-column>-->
<!--			</el-table>-->
<!--			<div v-else class="txt">{{message}}</div>-->
<!--			<div class="paginations">-->
<!--				<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"-->
<!--					:current-page="currentPage" :page-sizes="[10, 20, 40]" :page-size="pagesize"-->
<!--					layout="total, sizes, prev, pager, next, jumper" :total="tableData.length">-->
<!--				</el-pagination>-->
<!--			</div>-->
<!--		</div>-->


      <div class="title">
        <el-statistic group-separator="," :precision="2"  :title='"ISSUES 生成词云"'></el-statistic>
      </div>
    <div id="echartsWordcloud" style="width:1200px;height:650px;"></div>

    <el-empty description="  "></el-empty>

	</div>
</template>

<script>
import HistoryDialong from '../components/HistoryDialong.vue'
import $ from 'jquery'
import UserDialong from '../components/UserDialong.vue'
import echarts from "echarts";
import "echarts-wordcloud/dist/echarts-wordcloud";
import "echarts-wordcloud/dist/echarts-wordcloud.min";
import Header from '@/components/Header'
import LeftMenu from '@/components/LeftMenu'
let list=[]
let list2=[]
export default {
  name: 'FundList',
  list2: undefined,
  list3: undefined,

  data () {
    return {
      like: true,


      value1: list[0],
      // value2: list[1],
      value2: list[1],


      value3: list[2],
      value4: list[3],
      value5: list[4],
      title1: "Opened Issues",
      title2: "Closed Issues",
      title3: "time Range",
      title4: "time Mean",
      title5: "time Deviation",
      input: "Hello Element UI!",

      visible: false,
      message: '数据不存在',
      search_data: {
        startDate: '',
        endDate: ''
      },
      currentPage: 1, // 初始页
      pagesize: 10,
      tableData: [
      //     {
      //   name: "小区",
      //   value: "283"
      // },
      ], // 分页数据
    }
  },

  created () {
    const _this=this
    this.$axios.get('http://localhost:8080/issues').then(resp=>{
      console.log(1)
      console.log(this.tableData)
      this.list=resp.data
      console.log(this.list)
      this.value1=this.list[0]
      this.value2=this.list[1]
      this.value3=this.list[2]
      this.value4=this.list[3]
      this.value5=this.list[4]
      this.list2=this.list[5]
      this.list3=this.list[6]
      for(let x=0;x<150;x++){
            this.tableData[x]={
              name:this.list2[x].toString(),
              value:this.list3[x].toString()
            }
      }
      console.log(_this.initEcharts().data)
    })
  },

  mounted(){
    this.initEcharts()
  },
  methods: {
    initEcharts(data){
      let echartsWordcloud=this.$echarts.init(document.getElementById("echartsWordcloud"));
      let option = {
        title: {
          text: "",
          x: "center"
        },
        series: [
          {
            type: "wordCloud",
            //用来调整词之间的距离
            gridSize: 12,
            //用来调整字的大小范围
            sizeRange: [30, 160],
            rotationRange: [0, 0],
            //随机生成字体颜色
            textStyle: {
                color: function() {
                  return (
                      "rgb(" +
                      Math.round(Math.random() * 255) +
                      ", " +
                      Math.round(Math.random() * 255) +
                      ", " +
                      Math.round(Math.random() * 255) +
                      ")"
                  );
                }

            },
            //位置相关设置
            left: "center",
            top: "center",
            right: null,
            bottom: null,
            width: "200%",
            height: "200%",
            //数据
            data: this.tableData
          }
        ]
      };
      echartsWordcloud.setOption(option);
      //点击事件
      echartsWordcloud.on("click",function(e){})
    },
    handleSizeChange: function (size) {
      this.pagesize = size
      console.log(this.pagesize) // 每页下拉显示数据
    },
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
      console.log(this.currentPage) // 点击第几页
    }
  },
  components: {
    HistoryDialong,
  }
}
</script>
<style scoped>

.els{
  border-left: 1000px solid white;
}


  .table_container2{
    border-top: 15px solid white;
  }

.like {
  cursor: pointer;
  font-size: 25px;
  display: inline-block;
}

	.fundlist {
		padding: 10px;
	}

	.btnleft {
		float: left;
		margin-left: 20px;
	}

	.paginations {
		text-align: right;
		margin-top: 10px;
	}

	.txt {
		padding: 20px 0;
		background: #E7E6E6;
		font-size: 16px;
		text-align: center;
		color: #606266;
	}
  .add {
    color: white;
    background-color: darkred;
    border-color: darkred;
  }

.title0{
  border-top: 40px solid white;
  border-left: 150px solid white;
}
.title{
  border-top: 80px solid white;
}
</style>
