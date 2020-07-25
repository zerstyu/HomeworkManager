<template>
        <div class="row justify-content-center">
            <div class="col-lg-5 col-md-7">
                <div class="card bg-secondary shadow border-0">
                    <div class="card-header bg-transparent pb-5">
                        <div class="text-muted text-center mt-2 mb-3"><small>간편 로그인</small></div>
                        <div class="btn-wrapper text-center">
                            <a href="#" class="btn btn-neutral btn-icon">
                                <span class="btn-inner--icon"><img src="img/icons/common/github.svg"></span>
                                <span class="btn-inner--text">Github</span>
                            </a>
                            <a href="#" class="btn btn-neutral btn-icon">
                                <span class="btn-inner--icon"><img src="img/icons/common/google.svg"></span>
                                <span class="btn-inner--text">Google</span>
                            </a>
                        </div>
                    </div>
                    <div class="card-body px-lg-5 py-lg-5">
                        <div class="text-center text-muted mb-4">
                            <small>이메일로 로그인</small>
                        </div>
                        <form role="form">
                            <base-input class="input-group-alternative mb-3"
                                        placeholder="Email"
                                        addon-left-icon="ni ni-email-83"
                                        v-model="model.email">
                            </base-input>

                            <base-input class="input-group-alternative"
                                        placeholder="Password"
                                        type="password"
                                        addon-left-icon="ni ni-lock-circle-open"
                                        v-model="model.password">
                            </base-input>

                            <base-checkbox class="custom-control-alternative">
                                <span class="text-muted">암호를 기억하시겠습니까?</span>
                            </base-checkbox>
                            <div class="text-center">
                                <base-button v-on:click="loginReq()" type="primary" class="my-4">로그인</base-button>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row mt-3">
                    <div class="col-6">
                        <a href="#" class="text-light"><small>패스워드를 잊으셨나요?</small></a>
                    </div>
                    <div class="col-6 text-right">
                        <router-link to="/register" class="text-light"><small>회원가입</small></router-link>
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
                    <base-button type="primary" @click="goLoginPage()" href="/#/login">로그인 하러가기</base-button>
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
                        location.href='/#/homeworkManage';
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
