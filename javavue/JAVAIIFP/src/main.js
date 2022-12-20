import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import * as echarts from 'echarts'
Vue.prototype.$echarts = echarts
require('echarts-wordcloud')
Vue.prototype.$echarts = echarts

Vue.prototype.$axios = axios;
import axios from 'axios'
import VueAxios from 'vue-axios'

Vue.prototype.axios = axios;
Vue.use(VueAxios, axios);


//全局时间格式化过滤
Vue.filter('moment', function (value, formatString) {
    formatString = formatString || 'YYYY-MM-DD HH:mm:ss';
    return moment(value).format('YYYY-MM-DD');
});


Vue.use(ElementUI)
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
