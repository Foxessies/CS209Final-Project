<template>
	<div class="dialong">
		<el-dialog :title="dialog.title" type="primary" size="small" :close-on-press-escape="false"
			:modal-append-to-body="false" :close-on-click-modal="false" :visible.sync="dialog.show">
			<el-form :model="formDate" ref="formdoalog" label-width="100px">
				<el-form-item label="ID" prop="id">
					<el-input type="text" v-model="formDate.id"></el-input>
				</el-form-item>
				<el-form-item label="类别" prop="type">
					<el-input type="text" v-model="formDate.type"></el-input>
				</el-form-item>
				<el-form-item label="权限" prop="quanxian">
					<el-input type="text" v-model="formDate.quanxian"></el-input>
				</el-form-item>
				<el-form-item label="状态" prop="status">
					<el-input type="text" v-model="formDate.status"></el-input>
				</el-form-item>
				<el-form-item label="加入时间" prop="time">
					<el-input type="text" v-model="formDate.time"></el-input>
				</el-form-item>
				<el-form-item label="是否禁用" prop="isdelete">
					<el-input type="text" v-model="formDate.isdelete"></el-input>
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
		name: "UserDialong",
		props: {
			dialog: Object,
			formDate: Object
		},
		data() {
			return {


			};
		},
		methods: {
			dialongAdd(formdoalog) {
				this.dialog.show = false;
				// 更新数据
				this.$emit("update", this.formDate); //传递父组件,进行视图更新
				//情况内容
				this.formDate = "";
			}
		}
	};
</script>
<style scoped>
</style>
