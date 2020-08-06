<template>
        <div class="row justify-content-center">
            <div class="col-lg-5 col-md-7">
                <div class="card bg-secondary shadow border-0">
                    <div class="card-header bg-transparent pb-5">
                        <h2>과제에 참여하세요 !</h2>
                        <h3>{{masterName}}님의 {{subjectName}} 과목 입니다.</h3>
                        <div class="text-center">
                            <base-button v-on:click="joinReq()" type="primary" class="my-4">참여하기</base-button>
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
                    과목 가입에 성공하였습니다!
                </div>
                <template slot="footer">
                    <base-button type="primary" @click="goMainPage()">과제 확인하기</base-button>
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
          subjectName: '',
          masterName: '',
          subjectId: '',
        modals: false,
        modals2: false
      }
    },
    mounted() {
        let vm = this;
        //this.$route.params.inviteCode;
        axios.get('/api/subjects/invites?inviteCode='+ this.$route.params.inviteCode)
            .then(function(response){

                if(response.data.statusCode == 'OK'){
                    vm.subjectName = response.data.data.subjectName;
                    vm.masterName = response.data.data.userName;
                    vm.subjectId = response.data.data.subjectId;
                }
                else{
                    vm.modals = true;
                    vm.responseMsg = response.data.message;
                }
            });
    },
    methods: {
        joinReq(){
            //로그인 유효성 체크
            if(localStorage.getItem('userId') == '' ||
                localStorage.getItem('userId') == null){
                this.responseMsg = "로그인이 필요한 서비스 입니다. 로그인 후 다시 시도해 주세요!";
                this.modals = true;
                return;
            }

            let vm = this;
            const axiosConfig = { headers:{ "Content-Type": "application/json"} };

            axios.post('/api/joinSubjects',
                '{' +
                '"subjectId": "' + vm.subjectId + '",' +
                '"userId": "' + localStorage.getItem('userId') + '"' +
                '}'
                , axiosConfig)
                .then(function(response){

                    if(response.data.statusCode == 'OK'){
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
            location.href='/#/homeworkManage';
        }
    }
  }
</script>
<style>
</style>
