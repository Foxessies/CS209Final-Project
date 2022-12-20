/* eslint-disable */
import Vue from 'vue'
import Router from 'vue-router'
import axios from 'axios'
import qs from 'qs'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      redirect: 'first'
    },
	{
	  path: '/first',
	   component: () => import('./views/Home.vue'),
	},
    {
      path: '/index',
      name: 'Index',
      component: () => import('./views/Index.vue'),
      meta: { title: 'developer' },
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: 'home',
          component: () => import('./views/Home/developer'),
          meta: { title: 'developer' }
        },
        {
          path: '/user',
          name: 'user',
		    component: () => import('./views/isuess.vue'),
          meta: { title: 'Isuess' }
        },
        {
          path: '/history',
          name: 'hostory',
		    component: () => import('./views/releases.vue'),
          meta: { title: 'releases' }
        },
        {
          path: '/warning',
          name: 'warning',
          component: () => import('./views/other.vue'),
          meta: { title: 'other' }
        },
       ]
    },
    {
      path: '/index1',
      name: 'Index1',
      component: () => import('./views/Index1.vue'),
      meta: { title: 'developer' },
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: 'home',
          component: () => import('./views/Home/developer'),
          meta: { title: 'developer' }
        },
        {
          path: '/user',
          name: 'user',
          component: () => import('./views/isuess.vue'),
          meta: { title: 'Isuess' }
        },
        {
          path: '/history',
          name: 'hostory',
          component: () => import('./views/releases.vue'),
          meta: { title: 'releases' }
        },
        {
          path: '/warning',
          name: 'warning',
          component: () => import('./views/other.vue'),
          meta: { title: 'other' }
        },
      ]
    },

    {
      path: '/index2',
      name: 'Index2',
      component: () => import('./views/Index.vue'),
      meta: { title: 'developer' },
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: 'home',
          component: () => import('./views/Home/developer'),
          meta: { title: 'developer' }
        },
        {
          path: '/user',
          name: 'user',
          component: () => import('./views/isuess.vue'),
          meta: { title: 'Isuess' }
        },
        {
          path: '/history',
          name: 'hostory',
          component: () => import('./views/releases.vue'),
          meta: { title: 'releases' }
        },
        {
          path: '/warning',
          name: 'warning',
          component: () => import('./views/other.vue'),
          meta: { title: 'other' }
        },
      ]
    },

    {
      path: '*',
      name: 'Nofind',
      component: () => import('./views/404')
    }
  ]
  // mode: "history"
})


export default router
