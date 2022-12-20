<!--界面0内-->
<template>
  <div class="home">
    <div class="title">
      <el-row :gutter="100">
        <el-col :span="10">
          <div class="data">
            <el-statistic group-separator="," :precision="2" :value="value1" :title="title1"></el-statistic>
          </div>
        </el-col>
      </el-row>
    </div>

    <div class="title">
      <el-statistic group-separator="," :precision="2"  :title='"[[ major developers (前十名) ]]"'></el-statistic>
    </div>

    <div class="table_container2" style="margin-left: 20px;">
      <el-col :sapn="8" v-for="(item) in tableData" :key="item.id" :offset="1">
        <el-card shadow="hover" class="box-card1" style="min-height: 120px;" align="left">
          <div>
            <el-row :gutter="20">
            <el-col :span="8">
              <div class="peop">
                <el-avatar :size="80" :src="item.login"></el-avatar>
              </div>

            </el-col>
            <el-col :span="16">
              <div >
                {{'Name: '+item.name}}
              </div>
            <div >
              {{'ID: '+item.id}}
            </div>
              <div >
                {{'Address: '+item.email}}
              </div>
              <div >
                {{'Repo URL: '+item.repos_url}}
              </div>
            </el-col>
          </el-row>
          </div>
        </el-card>
      </el-col>
    </div>
    <div class="x">
      <el-empty description="  "></el-empty>
    </div>


  </div>
</template>

<script>

export default {
  name: 'FundList',
  list:undefined,
  data () {
    return {
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",


      like: true,
      value1: 4154.564,

      title1: "Total developers",

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
      //   'name':'xx',
      //   'email':'xx',
      //   'repos_url':'xx',
      //   'id':'xx',
      //   'login':"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      // },
      ], // 分页数据
    }
  },

  components: {

  },
  created () {
    const _this=this
    this.$axios.get('http://localhost:8080/committers').then(resp=>{
      this.list=resp.data
      this.value1=this.list[0]
      this.tableData=this.list[1]
      console.log(this.tableData)
      // console.log(_this.initEcharts().data)
    })
  },
}
</script>
<style scoped>

.peop{
  border-left: 100px solid white;
  border-right: 300px solid white;
}

.title{
  border-top: 15px solid white;
}

.table_container2{
  border-top: 15px solid white;
  border-bottom: 1000px solid white;
  width: auto;
  height: 100%;
  box-sizing: border-box;
  background-color: #E7E6E6;
  text-align: center;
}

home {
  width: auto;
  height:100%;
}
.container {
  width: auto;
  height: 100%;
  box-sizing: border-box;
  background-color: #E7E6E6;
  text-align: center;

}
.box-card1 {
  width: 200px;
  height: 10px;
  min-height: 10px;
  min-width: 950px;
  margin-right:20px;
  transition: all .5s;
  font-family: Arial,serif;
  border-top: 15px solid white;
}
.box-card1:hover{
  margin-top: -5px;
}
.x{
  border-top: 200px solid white;
}

</style>
