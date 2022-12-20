<template>
	<div class="tags" v-if="showTags">
		<ul>
<!--			<li class="tags-li" v-for="(item,index) in tagsList" :class="{'active': isActive(item.path)}" :key="index">-->
<!--				<router-link :to="item.path" class="tags-li-title">-->
<!--					{{item.title}}-->
<!--				</router-link>-->
<!--				<span class="tags-li-icon" @click="closeTags(index)"><i class="el-icon-close"></i></span>-->
<!--			</li>-->
		</ul>
		<div class="tags-close-box">
<!--			<el-dropdown @command="handleTags">-->
<!--				<el-button size="mini" type="primary" class="ebu">-->
<!--					标签选项<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
<!--				</el-button>-->
<!--				<el-dropdown-menu size="small" slot="dropdown">-->
<!--					<el-dropdown-item command="other">关闭其他</el-dropdown-item>-->
<!--					<el-dropdown-item command="all">关闭所有</el-dropdown-item>-->
<!--				</el-dropdown-menu>-->
<!--			</el-dropdown>-->
		</div>
	</div>
</template>

<script>
	import bus from './bus';
	export default {
		data() {
			return {
				tagsList: []
			}
		},
		methods: {
			isActive(path) {
				return path === this.$route.fullPath;
			},
			// 关闭单个标签
			closeTags(index) {
				const delItem = this.tagsList.splice(index, 1)[0];
				const item = this.tagsList[index] ? this.tagsList[index] : this.tagsList[index - 1];
				if (item) {
					delItem.path === this.$route.fullPath && this.$router.push(item.path);
				} else {
					this.$router.push('/');
				}
			},
			// 关闭全部标签
			closeAll() {
				this.tagsList = [];
				this.$router.push('/');
			},
			// 关闭其他标签
			closeOther() {
				const curItem = this.tagsList.filter(item => {
					return item.path === this.$route.fullPath;
				})
				this.tagsList = curItem;
			},
			// 设置标签
			setTags(route) {

				const isExist = this.tagsList.some(item => {
					return item.path === route.fullPath;
				})

				if (!isExist) {
					if (this.tagsList.length >= 8) {
						this.tagsList.shift();
					}
					this.tagsList.push({
						title: route.meta.title,
						path: route.fullPath,
						name: route.matched[1].components.default.name
					})
				}
				bus.$emit('tags', this.tagsList);
				// console.log(this.tagsList)
			},
			handleTags(command) {
				command === 'other' ? this.closeOther() : this.closeAll();
			}
		},
		computed: {
			showTags() {
				return this.tagsList.length > 0;
			}
		},
		watch: {
			$route(newValue, oldValue) {
				this.setTags(newValue);
			}
		},
		created() {
			this.setTags(this.$route);
		}
	}
</script>


<style scoped>
	.tags {
		display: inline-block;
		margin-left: 20px;
		height: 30px;
		width: 100%;
		background: #E7E6E6;
		padding-right: 120px;
		box-shadow: 0 5px 10px black;
	}

	.tags ul {
		box-sizing: border-box;
		width: 100%;
		height: 100%;
	}

	.tags-li {
		float: left;
		margin: 3px 5px 2px 3px;
		border-radius: 3px;
		font-size: 12px;
		overflow: hidden;
		cursor: pointer;
		height: 23px;
		line-height: 23px;
		border: 1px solid black;
		background: #E7E6E6;
		padding: 0 5px 0 12px;
		vertical-align: middle;
		color: #666;
		-webkit-transition: all .3s ease-in;
		-moz-transition: all .3s ease-in;
		transition: all .3s ease-in;
	}

	.tags-li:not(.active):hover {
		background: #E7E6E6;
	}

	.tags-li.active {
		color: white;
		background: #000080;
	}

	.tags-li-title {
		float: left;
		max-width: 80px;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		margin-right: 5px;
		color: black;
	}

	.tags-li.active .tags-li-title {
		color: #E7E6E6;
	}

	.tags-close-box {
		position: absolute;
		right: 0;
		top: 0;
		box-sizing: border-box;
		padding-top: 1px;
		text-align: center;
		width: 110px;
		height: 30px;
		background: #E7E6E6;
		box-shadow: -3px 0 15px 3px black;
		z-index: 10;
	}
  .ebu {
    color: black;
    background-color: #E7E6E6;
    border-color: #E7E6E6;
  }
</style>
