<!--主界面-->
<template>
	<div class="main">
	<div class="head">
		<span class="head-title" >JavaII.FinalProject</span >
<!--		<el-input placeholder="Example:OrderedDict.iteritems" v-model="input3" class="input-with-select" >-->
<!--		    <el-select v-model="select" slot="prepend" style="color: black;" placeholder="Python" >-->
<!--		      <el-option label="xxxxx" value="1" ></el-option>-->
<!--		      <el-option label="xx" value="2"></el-option>-->
<!--		      <el-option label="xxx" value="3"></el-option>-->
<!--		    </el-select>-->
<!--			<el-button slot="append" style="background-color: #7795B9;padding: 8px;width:100px: #50628C;color: #E7E6E6;">Search</el-button>-->
<!--			</el-input>-->
			<el-dropdown style="float: right;margin-right: 20px;margin-left: 150px;">
			      <span class="el-dropdown-link">
			        EN<i class="el-icon-arrow-down el-icon--right"></i>
			      </span>
			      <el-dropdown-menu slot="dropdown">
			        <el-dropdown-item >EN</el-dropdown-item>
			      </el-dropdown-menu>
			    </el-dropdown>
	</div>
	<div class="main">
		<span class="main-content">GitHub is a website for developers to store and manage their code.</span>
		<br />
		<span class="main-content1">Developers could also use GitHub to track the releases, versions, issues, commits (code changes) and discussions of their projects.  </span>
	    <br><span class="main-content1"> For a given GitHub repository, we are interested in the following questions.</span>
		 <br><span class="main-content1" style="font-weight: bolder;">Developers : </span><span>Damien Arrachequesne (main)</span>
		  <br><span class="main-content1" style="font-weight: bolder;">Issues: </span><span>4098 times</span>
		   <br><span class="main-content1" style="font-weight: bolder;">Releases : </span><span>53</span>
		    <br><span class="main-content1" style="font-weight: bolder;">Commits: </span><span>1589 times (after the last release)</span>
<!--			 <br><span class="main-content1" style="font-weight: bolder;">Examples at : </span><el-link href="https://github.com/socketio/socket.io" target="_blank">https://github.com/socketio/socket.io</el-link>-->
			<div style="position: fixed;bottom:220px;z-index: 100;right: 20px;">
				<el-link style="color: black;font-size: 16px;"   :underline="false"
					@click="toIndex()">repo more detail>>>
				</el-link>
			</div>

<!--    <div style="position: fixed;bottom:220px;z-index: 100;right: 20px;">-->
<!--      <el-link style="color: black;font-size: 16px;"   :underline="false"-->
<!--               @click="toIndex2()">repo3 more detail>>>-->
<!--      </el-link>-->
<!--    </div>-->
	</div>

	</div>
</template>

<script>
	export default {
	  data() {
	    return {
        params: {
          OWNER: undefined,
          REPO: undefined,
        }
	    }
	  },
	  methods:{

		  toIndex () {
        this.$router.push('/index')
      },
      // async fetch() {
      //   this.$router.push('/index')
      //   this.OWNER = "node-fetch"
      //   this.REPO = "node-fetch"
      //   this.params={
      //         OWNER: this.OWNER,
      //         REPO: this.REPO,
      //   }
      //   const { data: resp } = await this.$http.post('http://localhost:8080/repo',this.params);
      // },
      // async toIndex1 () {
      //   this.$router.push('/index')
      //   this.OWNER = "node-fetch"
      //   this.REPO = "node-fetch"
      //   // const { data: res } = await this.$http.get('http://localhost:8080/repo',
      //   //     {
      //   //       OWNER: this.OWNER,
      //   //       REPO:this.REPO
      //   //     })
      // },
      toIndex2(){
        this.$router.push('/index2')
      },
      goPage(url){
        window.location.href=url
      }


	  },

	}
</script>

<style scoped>
	.main{
		height: 100%;
		width: 100%;
		overflow: hidden;
		background: url('../assets/first.jpg')  center/cover;
	}
	.head{
		width: 100%;
		height: 50px;
		background-color: black;
		text-align: center;
		line-height: 50px;
	}
	.head-title{
		color: #E7E6E6;
		font-size: 25px;
		display: inline-block;
		float: left;
		margin-left: 100px;
    font-family: Helvetica,serif;
	}
	.input-with-select{
		width:  60%;
	}
	/deep/ .el-input__inner{
	    height: 30px;
	}
/deep/ .el-select .el-input {
    width: 90px;
  }
 /deep/  .el-input__suffix {
    top: 9px;
  }
  /deep/  .el-input__icon {
    line-height: inherit;
  }
  /deep/  .el-input__suffix-inner {
    display: inline-block;
  }
.el-input-group__prepend {
    background-color: red;
  }
  .main-content{
	  display: inline-block;
	  margin-top: 250px;
	  margin-left: 30px;
	  font-size: 28px;
	  font-weight: bolder;
	  margin-bottom: 100px;
  }
  .main-content1{
	  display: inline-block;
	  margin-left: 32px;
	  margin-bottom: 10px;
  }
</style>
