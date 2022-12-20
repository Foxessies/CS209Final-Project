<!--界面4-->
<template>
  <div class="fundlist">
    <div>
      <el-form :inline="true" ref="search_data" :model="search_data">
        <el-form-item class="btnleft">
          <el-button type="primary" size="small" class="add" icon="el-icon-edit-outline" @click='onAddMoney()'>添加
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="table_container2" style="margin-left: 20px;">
      <el-table v-if="tableData.length > 0" :data="tableData.slice((currentPage-1)*pagesize,currentPage*pagesize)"
                min-height="460px" border style="width: 100%">
        <el-table-column label="时间" align='center' width="300">
          <template slot-scope="scope">
            <span>{{ scope.row.time}}</span>
          </template>
        </el-table-column>
        <el-table-column label="设备号" align="center" width="200">
          <template slot-scope="scope">
            <span>{{ scope.row.number}}</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" width="160">
          <template slot-scope="scope">
            <span>{{ scope.row.status}}</span>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.note}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope.$index, scope.row)">编辑
            </el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除
            </el-button>

          </template>
        </el-table-column>
      </el-table>
      <div v-else class="txt">{{message}}</div>
      <div class="paginations">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                       :current-page="currentPage" :page-sizes="[5, 10, 20, 40]" :page-size="pagesize"
                       layout="total, sizes, prev, pager, next, jumper" :total="tableData.length">
        </el-pagination>
      </div>
    </div>
    <Dialong :dialog="dialog" :formDate="formDate" @update="update"></Dialong>
  </div>
</template>

<script>
import Dialong from '../components/Dialong.vue'
export default {
  name: 'FundList',
  data () {
    return {
      visible: false,
      message: '数据不存在',
      search_data: {
        startDate: '',
        endDate: ''
      },
      currentPage: 1, // 初始页
      pagesize: 5,
      tableData: [{
        'time': '2022/10/11',
        'number': 1,
        'status': '故障',
        'note': '近故障运行，请及时检修'
      },
        {
          'time': '2022/10/13',
          'number': 3,
          'status': '故障',
          'note': '近故障运行，请及时检修'
        },
        {
          'time': '2022/10/14',
          'number': 4,
          'status': '故障',
          'note': '近故障运行，请及时检修'
        },
        {
          'time': '2022/10/16',
          'number': 2,
          'status': '故障',
          'note': '近故障运行，请及时检修'
        }
      ], // 分页数据
      dialog: { // 弹出框
        title: '',
        show: false,
        option: 'edit'
      },
      formDate: { // 添加编辑删除需要传的字段
        time: '',
        number: '',
        status: '',
        note: ''
      }
    }
  },
  methods: {
    handleEdit (index, row) { // 编辑信息
      this.dialog = {
        title: '编辑信息',
        show: true,
        option: 'edit'
      }
      this.formDate = {
        index: index,
        time: row.time,
        number: row.number,
        status: row.status,
        note: row.note
      }
    },
    handleDelete (index, row) { // 删除数据
      this.tableData.splice(index, 1)
    },
    update (val) {
      if (val.index === -1) {
        this.tableData.push(val)
      } else {
        this.tableData[val.index].time = val.time
        this.tableData[val.index].number = val.number
        this.tableData[val.index].status = val.status
        this.tableData[val.index].note = val.note
      }
    },
    getDeleteVisible (index, row) {
      this.visible = false // 隐藏弹出框
    },
    onAddMoney () { // 添加信息
      this.dialog = {
        title: '添加信息',
        show: true,
        option: 'add'
      }
      this.formDate = {
        index: -1,
        time: '',
        number: '',
        status: '',
        note: ''
      }
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
    Dialong
  }
}
</script>
<style scoped>
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
</style>
