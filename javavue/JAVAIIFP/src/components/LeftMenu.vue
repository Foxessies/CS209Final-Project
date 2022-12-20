<template>
	<div class="sidebar">

			<el-row class="menu_page">
				<el-col>
					<el-menu
						mode="vertical"
						class="el-menu-vertical-demo"
						:collapse="collapse"
						:default-active="$route.path"
						background-color="black"
						text-color="#fff">
						<div class="wrapper" ref="wrapper">
							<Menu :items='items'/>
						</div>
					</el-menu>
				</el-col>
			</el-row>

	</div>
</template>

<script>
import bus from '../common/bus'
import Menu from './Menu'
import Bscroll from 'better-scroll'
// @ is an alias to /src
export default {
  name: 'LeftMenu',
  data () {
    return {
      collapse: false,
      items: [
        {
          icon: 'el-icon-menu',
          name: 'Developers',
          path: '/home'
        },
        {
          icon: 'el-icon-user-solid',
          name: 'Issues',
          path: '/user'
        },
        {
          icon: 'el-icon-s-data',
          name: 'Releases & Commits',
          path: '/history'
        },
        // {
        //   icon: 'el-icon-warning',
        //   name: 'Other',
        //   path: '/warning'
        // }

      ]
    }
  },
  components: {
    Menu
  },
  methods: {
    initScroll () {
      this.scroll = new Bscroll(this.$refs.wrapper, {
      })
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.initScroll()
    })
  },
  created () {
    // 通过 Event Bus 进行组件间通信，来折叠侧边栏
    bus.$on('collapse', msg => {
      this.collapse = msg
    })
  }
}
</script>
<style scoped>
	.menu_page {
		position: fixed;
		top: 71px;
		left: 0;
		min-height: 100%;
		background-color: black;
		z-index: 99;
	}
	.sidebar {
		position: relative;
		overflow: hidden;
	}
	.wrapper {
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		width: 100%;
		height: 600px;
		min-height: 500px;
		overflow: hidden;
	}
	.sidebar::-webkit-scrollbar {
		width: 0;
	}

	.sidebar-el-menu:not(.el-menu--collapse) {
		width: 250px;
	}

	.el-menu {
		border: none;

	}

	.fa-margin {
		margin-right: 5px;
	}

	.el-menu-vertical-demo:not(.el-menu--collapse) {
		width: 200px;
		min-height: 400px;
	}

	.el-menu-vertical-demo {
		width: 49px;
	}

	.el-submenu .el-menu-item {
		min-width: 180px;
	}

	.hiddenDropdown,
	.hiddenDropname {
		display: none;
	}

	a {
		text-decoration: none;
	}
</style>
