import Vue from 'vue'
import Router from 'vue-router'
import DashboardLayout from '@/layout/DashboardLayout'
import AuthLayout from '@/layout/AuthLayout'
Vue.use(Router)

export default new Router({
  linkExactActiveClass: 'active',
  routes: [
    {
      path: '/',
      //redirect: 'dashboard',
      redirect: 'login',
      component: DashboardLayout,
      children: [
        {
          //path: '/dashboard',
          //name: 'dashboard',
          path: '/homeworkManage',
          name: '과제관리',
          // route level code-splitting
          // this generates a separate chunk (about.[hash].js) for this route
          // which is lazy-loaded when the route is visited.
          //component: () => import(/* webpackChunkName: "demo" */ './views/Dashboard.vue')
          component: () => import(/* webpackChunkName: "demo" */ './views/HomeworkManage.vue')
        },
        {
          path: '/icons',
          name: 'icons',
          component: () => import(/* webpackChunkName: "demo" */ './views/Icons.vue')
        },
        {
          path: '/homeworkEdit/:assignId',
          name: '채점하기',
          component: () => import('./views/HomeworkEdit.vue')
        },
        {
          path: '/homeworkDetail/:assignId',
          name: '제출과제 상세보기',
          component: () => import('./views/HomeworkDetail.vue')
        },
        {
          path: '/statistics',
          name: '통계자료',
          component: () => import('./views/Statistics.vue')
        },
        {
          path: '/openNotice',
          name: '공개과제',
          component: () => import('./views/OpenNotice.vue')
        },
        {
          path: '/wrongAnswerNotes',
          name: '오답노트',
          component: () => import('./views/WrongAnswerNotes.vue')
        },
        {
          path: '/homeworkManage',
          name: '과제 리스트',
          component: () => import(/* webpackChunkName: "demo" */ './views/HomeworkManage.vue')
        },
        {
          path: '/notice/:notiId',
          name: '출제 과제',
          component: () => import(/* webpackChunkName: "demo" */ './views/Notice.vue')
        },
        {
          path: '/profile',
          name: 'profile',
          component: () => import(/* webpackChunkName: "demo" */ './views/UserProfile.vue')
        },
        {
          path: '/maps',
          name: 'maps',
          component: () => import(/* webpackChunkName: "demo" */ './views/Maps.vue')
        },
        {
          path: '/tables',
          name: 'tables',
          component: () => import(/* webpackChunkName: "demo" */ './views/Tables.vue')
        }
      ]
    },
    {
      path: '/',
      redirect: 'login',
      component: AuthLayout,
      children: [
        {
          path: '/login',
          name: 'login',
          component: () => import(/* webpackChunkName: "demo" */ './views/Login.vue')
        },
        {
          path: '/register',
          name: 'register',
          component: () => import(/* webpackChunkName: "demo" */ './views/Register.vue')
        },
        {
          path: '/subjectJoin/:inviteCode',
          name: '과목 가입',
          component: () => import(/* webpackChunkName: "demo" */ './views/SubjectJoin.vue')
        }
      ]
    }
  ]
})
