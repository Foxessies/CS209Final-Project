<template>
 <div class="dialong">
	 <el-dialog :title="dialog.title" type="primary" size="small" :close-on-press-escape="false"
			:modal-append-to-body="false" :close-on-click-modal="false" :visible.sync="dialog.show">
			<el-form :model="formDate" ref="formdoalog" label-width="100px">
				<el-form-item label="TTTTTT" prop="time">
					<el-input type="text" v-model="formDate.time"></el-input>
				</el-form-item>
				<el-form-item label="A" prop="awendu">
					<el-input type="text" v-model="formDate.awendu"></el-input>
				</el-form-item>
				<el-form-item label="B" prop="bwendu">
					<el-input type="text" v-model="formDate.bwendu"></el-input>
				</el-form-item>
				<el-form-item label="C" prop="cwendu">
					<el-input type="text" v-model="formDate.cwendu"></el-input>
				</el-form-item>
				<el-form-item label="D" prop="twendu">
					<el-input type="text" v-model="formDate.twendu"></el-input>
				</el-form-item>
				<el-form-item label="x轴加速度" prop="xsudu">
					<el-input type="text" v-model="formDate.xsudu"></el-input>
				</el-form-item>
				<el-form-item label="y轴加速度" prop="ysudu">
					<el-input type="text" v-model="formDate.ysudu"></el-input>
				</el-form-item>
				<el-form-item label="z轴加速度" prop="zsudu">
					<el-input type="text" v-model="formDate.zsudu"></el-input>
				</el-form-item>
				<el-form-item label="运行状态" prop="status">
					<el-input type="text" v-model="formDate.status"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button @click="dialog.show = false">取消</el-button>
					<el-button type="primary" @click="dialongAdd('formdoalog')">提交</el-button>
				</el-form-item>
			</el-form>
		</el-dialog>
	</div>
</template>

<script>
// @ is an alias to /src
export default {
  name: 'HistoryDialong',
  props: {
    dialog: Object,
    formDate: Object
  },
  data () {
    return {

    }
  },
  methods: {
    dialongAdd (formdoalog) {
      this.dialog.show = false
      // 更新数据
      this.$emit('update', this.formDate) // 传递父组件,进行视图更新
      // 情况内容
      this.formDate = ''
    }
  }
}
</script>
<style scoped>
</style>
