<template>
    <div class="row justify-content-center">
        <div class="col-lg-5 col-md-7">
            <div class="card bg-secondary shadow border-0">
                <div class="card-header bg-transparent pb-5">
                    <div class="text-muted text-center mt-2 mb-3">
                        <small>간편 회원가입</small>
                    </div>
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
                        <small>이메일로 회원가입</small>
                    </div>
                    <form role="form">

                        <base-input class="input-group-alternative mb-3"
                                    placeholder="소속"
                                    addon-left-icon="ni ni-hat-3"
                                    v-model="model.goupName">
                        </base-input>

                        <base-input class="input-group-alternative mb-3"
                                    placeholder="이름"
                                    addon-left-icon="ni ni-hat-3"
                                    v-model="model.name">
                        </base-input>

                        <base-input class="input-group-alternative mb-3"
                                    placeholder="Email"
                                    addon-left-icon="ni ni-email-83"
                                    v-model="model.email">
                        </base-input>

                        <base-input class="input-group-alternative"
                                    placeholder="Password"
                                    type="패스워드"
                                    addon-left-icon="ni ni-lock-circle-open"
                                    v-model="model.password">
                        </base-input>

                        <base-radio name="MAN" class="mb-3" v-model="model.radio1">
                            남자
                        </base-radio>

                        <base-radio name="WOMAN" class="mb-3" v-model="model.radio1">
                            여자
                        </base-radio>

                        <base-input class="input-group-alternative mb-3"
                                    placeholder="생년월일 (ex. 1992-01-11)"
                                    addon-left-icon="ni ni-zoom-split-in"
                                    v-model="model.birthday">
                        </base-input>

                        <!--div class="text-muted font-italic">
                            <small>암호 안전함 : <span class="text-success font-weight-700">strong</span></small>
                        </div-->

                        <div class="row my-4">
                            <div class="col-12">
                                <base-checkbox class="custom-control-alternative">
                                    <span class="text-muted">약관에 동의합니다. <a href="#!">[약관보기]</a></span>
                                </base-checkbox>
                            </div>
                        </div>
                        <div class="text-center">
                            <base-button v-on:click="joinReq()" type="primary" class="my-4">회원가입</base-button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="row mt-3">
                <div class="col-6">
                    <a href="#" class="text-light">
                        <small>비밀번호를 잊으셨나요?</small>
                    </a>
                </div>
                <div class="col-6 text-right">
                    <router-link to="/login" class="text-light">
                        <small>로그인하기</small>
                    </router-link>
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
    name: 'register',
    data() {
      return {
        model: {
          name: '',
          email: '',
          goupName: '',
          password: '',
          birthday: '',
          radio1: "WOMAN"
        },
        modals : false,
        modals2: false,
        responseMsg : ''
      }
    },
    methods: {
        joinReq(){
            let vm = this;

            /*
            let form = new FormData();
            form.append('email', vm.email);
            form.append('groupName', vm.name);
            form.append('password', vm.password);
            */

            const axiosConfig = { headers:{ "Content-Type": "application/json"} };

            axios.post('/api/users',
                '{' +
                        '"name": "' + vm.model.name + '",' +
                        '"email": "' + vm.model.email + '",' +
                        '"groupName": "' + vm.model.goupName + '",' +
                        '"gender": "' + vm.model.radio1 + '",' +
                        '"birthday": "' + vm.model.birthday + '",' +

                        '"password": "' + vm.model.password + '"' +
                      '}'
                , axiosConfig)
                .then(function(response){
                    console.log(response);


                    if(response.data.statusCode == 'OK'){
                        vm.modals2 = true;
                    }
                    else{
                        vm.modals = true;
                        vm.responseMsg = response.data.message;                    }
                });
        },
        goLoginPage(){
            this.modals2 = false;
            location.href='/#/login';
        }
    }

  }
</script>
<style>
</style>
