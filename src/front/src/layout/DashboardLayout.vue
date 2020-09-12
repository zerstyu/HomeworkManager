<template>
  <div class="wrapper" :class="{ 'nav-open': $sidebar.showSidebar }">
    <side-bar
      :background-color="sidebarBackground"
      short-title="Argon"
      title="Argon"
    >
      <template slot="links">
        <!--sidebar-item
          :link="{
            name: 'Dashboard',
            icon: 'ni ni-tv-2 text-primary',
            path: '/dashboard'
          }"
        /-->

        <!--sidebar-item :link="{name: 'Icons', icon: 'ni ni-planet text-blue', path: '/icons'}"/>
        <sidebar-item :link="{name: 'Maps', icon: 'ni ni-pin-3 text-orange', path: '/maps'}"/>
        <sidebar-item :link="{name: 'User Profile', icon: 'ni ni-single-02 text-yellow', path: '/profile'}"/>
        <sidebar-item :link="{name: 'Tables', icon: 'ni ni-bullet-list-67 text-red', path: '/tables'}"/-->
        <sidebar-item :link="{name: '과제관리', icon: 'ni ni-books text-blue', path: '/homeworkManage'}"/>
        <!--
        <sidebar-item :link="{name: '내가 만든 과목', icon: 'ni ni-hat-3 text-blue', path: '/'}"/>
        <sidebar-item :link="{name: '내가 참여한 과목', icon: 'ni ni-briefcase-24 text-blue', path: '/'}"/>
        -->
        <sidebar-item :link="{name: '통계', icon: 'ni ni-chart-bar-32 text-blue', path: '/statistics'}"/>
        <sidebar-item :link="{name: '공개과제', icon: 'ni ni-world text-blue', path: '/openNotice'}"/>
        <sidebar-item :link="{name: '오답노트', icon: 'ni ni-book-bookmark text-blue', path: '/wrongAnswerNotes'}"/>
        <sidebar-item :link="{name: '로그아웃', icon: 'ni ni-key-25 text-info', path: '/login'}"/>
        <!--sidebar-item :link="{name: '회원가입', icon: 'ni ni-circle-08 text-pink', path: '/register'}"/-->

      </template>
    </side-bar>
    <div class="main-content" :data="sidebarBackground">
      <dashboard-navbar></dashboard-navbar>

      <div @click="toggleSidebar">
        <fade-transition :duration="200" origin="center top" mode="out-in">
          <!-- your content here -->
          <router-view></router-view>
        </fade-transition>
        <content-footer v-if="!$route.meta.hideFooter"></content-footer>
      </div>
    </div>
  </div>
</template>
<script>
  import DashboardNavbar from './DashboardNavbar.vue';
  import ContentFooter from './ContentFooter.vue';
  import { FadeTransition } from 'vue2-transitions';

  export default {
    components: {
      DashboardNavbar,
      ContentFooter,
      FadeTransition
    },
    data() {
      return {
        sidebarBackground: 'vue' //vue|blue|orange|green|red|primary
      };
    },
    methods: {
      toggleSidebar() {
        if (this.$sidebar.showSidebar) {
          this.$sidebar.displaySidebar(false);
        }
      }
    }
  };
</script>
<style lang="scss">
</style>
