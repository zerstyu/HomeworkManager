<template>
        <div class="row justify-content-center">
            <div class="col-lg-5 col-md-7">
                <div class="card bg-secondary shadow border-0">
                    <div class="card-header bg-transparent pb-5">
                        <h2>과제에 참여하세요 !</h2>
                        <h3>추대윤님의 수학 과목 입니다.</h3>
                        <div class="text-center">
                            <base-button v-on:click="loginReq()" type="primary" class="my-4">로그인</base-button>
                        </div>
                    </div>
                </div>
            </div>
            <modal :show.sync="modals">
                <template slot="header">
                    <h5 class="modal-title" id="exampleModalLabel">알림</h5>
                </template>
                <div>
                    {{responseMsg}}
                </div>
                <template slot="footer">
                    <base-button type="secondary" @click="modals = false">확인</base-button>
                    <!--base-button type="primary">Save changes</base-button-->
                </template>
            </modal>

            <modal :show.sync="modals2">
                <template slot="header">
                    <h5 class="modal-title" id="exampleModalLabel2">알림</h5>
                </template>
                <div>
                    회원가입에 성공하였습니다.
                </div>
                <template slot="footer">
                    <base-button type="primary" @click="goLoginPage()" href="/login">로그인 하러가기</base-button>
                    <!--base-button type="primary">Save changes</base-button-->
                </template>
            </modal>
        </div>
</template>
<script>
const axios = require('axios');

export default {
    name: 'login',
    data() {
      return {
        model: {
          email: '',
          password: ''
        },
        modals: false,
        modals2: false
      }
    },
    methods: {
        loginReq(){
            let vm = this;

            const axiosConfig = { headers:{ "Content-Type": "application/json"} };

            axios.post('/api/login',
                '{' +
                '"email": "' + vm.model.email + '",' +
                '"password": "' + vm.model.password + '"' +
                '}'
                , axiosConfig)
                .then(function(response){

                    if(response.data.statusCode == 'OK'){
                        location.href='#';

                        localStorage.setItem('userId', response.data.data.id);
                        localStorage.setItem('userEmail', response.data.data.email);
                        localStorage.setItem('userName', response.data.data.name);
                        localStorage.setItem('userGroupName', response.data.data.groupName);

                        //location.href='/#/homeworkManage';
                        vm.$router.push({ path: 'homeworkManage' });
                        vm.modals2 = true;
                    }
                    else{
                        vm.modals = true;
                        vm.responseMsg = response.data.message;
                    }
                });
        },
        goMainPage(){
            this.modals2 = false;
            location.href='/#/login';
        }
    }
  }
</script>
<style>
</style>
