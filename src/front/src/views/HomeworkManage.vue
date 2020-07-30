<template>
    <div>
        <base-header type="gradient-success" class="pb-6 pb-8 pt-5 pt-md-8">
            <!-- Card stats -->
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <h3>내가 만든 과목</h3>
                </div>
                <div class="col-xl-3 col-lg-6"
                     v-for="teacherSubject in teacherSubjects" v-bind:key="teacherSubject.id"
                     @click="teacherSubjectsDetail(teacherSubject.idx)">
                    <stats-card v-bind:title="teacherSubject.userName + ' 선생님'"
                                v-bind:icon="teacherSubject.icon"
                                v-bind:sub-title="teacherSubject.subjectName"
                                class="mb-4 mb-xl-0">

                        <template slot="footer">
                            <!--span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 3.48%</span-->
                            <span class="text-nowrap">{{teacherSubject.inviteCode}}</span>
                            <base-button type="info" size="sm" href="#" @click="copyToClipboard(teacherSubject.inviteCodeURL)">URL 복사</base-button>
                        </template>
                    </stats-card>
                </div>

                <div class="col-xl-3 col-lg-6">
                    <stats-card title="남은과제 : 3/12"
                                icon="ni ni-check-bold"
                                sub-title="1학년 1반"
                                class="mb-4 mb-xl-0">

                        <template slot="footer">
                            <span class="text-nowrap">http://.2234234232342343423/4234</span>
                            <base-button type="info" size="sm" @click="copyToClipboard('복사잘돼나')">복사</base-button>
                        </template>
                    </stats-card>
                </div>

                <div class="col-xl-12 col-lg-12">
                    <br/>
                    <base-button type="primary" icon="ni ni-bag-17" v-on:click="modals2 = true">과목 만들기</base-button>
                </div>


                <div class="col-xl-12 col-lg-12">
                    <br/><br/>
                    <h3>내가 참여한 과목</h3>
                </div>
                <div class="col-xl-3 col-lg-6">
                    <stats-card title="남은과제 : 3/12"
                                type="gradient-orange"
                                sub-title="1학년 수학"
                                class="mb-4 mb-xl-0">

                        <template slot="footer">
                            <span class="text-nowrap">안재홍 선생님</span>
                        </template>
                    </stats-card>
                </div>
                <div class="col-xl-3 col-lg-6">
                    <stats-card title="남은과제 : 3/12"
                                type="gradient-orange"
                                sub-title="1학년 기술가정"
                                class="mb-4 mb-xl-0">

                        <template slot="footer">
                            <span class="text-nowrap">추대윤 선생님</span>
                        </template>
                    </stats-card>
                </div>

                <div class="col-xl-12 col-lg-12">
                    <br/>
                    <base-button type="primary" icon="ni ni-bag-17" id="subjectRoomJoinButton">오픈 과제물 찾기</base-button>
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
                    <h5 class="modal-title" id="exampleModalLabel2">과제방 만들기</h5>
                </template>
                <div>
                    <base-input placeholder="과제명을 입력해주세요" v-model="createSubjectName"></base-input>
                </div>
                <template slot="footer">
                    <base-button type="primary" @click="createSubject()">과제방 생성</base-button>
                    <base-button type="default" @click="modals2 = false">취소</base-button>
                </template>
            </modal>



        </base-header>

        <div class="container-fluid mt--7">
            <div class="row">
                <div class="col">
                    <projects-table
                            v-bind:now-user="nowUser"
                            v-bind:subject-pivot-name-edit="subjectPivotNameEdit" v-bind:subject-id="subjectPivot" v-bind:title="subjectPivotName" v-bind:subjectMasterId="subjectMasterId" v-bind:notice-data="noticeDataSend"></projects-table>
                </div>
            </div>
            <!--div class="row mt-5">
                <div class="col">
                    <projects-table type="dark" title="Dark Table"></projects-table>
                </div>
            </div-->
        </div>
        <!--div v-if="scrollOn" class="container-fluid mt--7">
            <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
        </div-->

    </div>

</template>
<script>
  import ProjectsTable from './Tables/ProjectsTable'
  import {BUS} from "./EventBus";

  const axios = require('axios');

  export default {
    name: 'tables',
    components: {
      ProjectsTable
    },
      data() {
          return {
              teacherSubjects : [],
              studentSubjects : [],
              modals: true,
              modals2: false,
              modals3: false,
              responseMsg: '데이터를 불러오고 있습니다.',
              createSubjectName: '',
              noticeDataSend : null,
              scrollOn : true,
              subjectMasterId : '',

              subjectPivot: '',
              subjectPivotName: '전체 과제리스트',
              subjectPivotNameEdit: '',
              nowUser: ''
          }
      },
      created() {
          this.getSubjects();
          this.getNotices();
          //let vm = this;
          //vm.modals = false;
/*
          setTimeout(function() {
              console.log('Works!');
              vm.scrollOn = false;
              vm.modals = false;
          }, 500);
*/
          BUS.$on('subjectUpdate',function() {
              this.getSubjects();
              this.getNotices();
          });

      },
      mounted() {
          this.modals = false;
      },
      methods: {
          createSubject(){
              this.modals2 = false;
              let vm = this;
              //let form = new FormData();
              //form.append('subjectName', this.createSubjectName);
              //form.append('userId', localStorage.getItem('userId'));
              const axiosConfig = { headers:{ "Content-Type": "application/json"} };

              axios.post('/api/subjects',
                  '{' +
                  '"subjectName": "' + vm.createSubjectName + '",' +
                  '"userId": "' + localStorage.getItem('userId') + '"' +
                  //'"userName": "' + localStorage.getItem('userName') + '"' +
                  '}'
                  //vm.createSubjectName
                  //form
                  , axiosConfig)
                  .then(function(response){
                      if(response.data.statusCode == 'OK'){
                          vm.modals = true;
                          vm.responseMsg = '성공하였습니다.';
                          vm.createSubjectName = '';
                          vm.getSubjects();
                      }
                      else{
                          vm.modals = true;
                          vm.responseMsg = response.data.message;
                      }
                  });
          },
          getSubjects(){
              let vm = this;

              axios.get('/api/subjects?userId=' + localStorage.getItem('userId'))
                  .then(function(response){
                      if(response.data.statusCode == 'OK'){

                          vm.teacherSubjects = [];
                          for(let i = 0; i < response.data.data.subjectDtoList.length; i++){
                              vm.teacherSubjects.push({
                                  userId : response.data.data.subjectDtoList[i].userId,
                                  userName : response.data.data.subjectDtoList[i].userName,
                                  subjectId :response.data.data.subjectDtoList[i].subjectId,
                                  subjectName :response.data.data.subjectDtoList[i].subjectName,
                                  inviteCode : response.data.data.subjectDtoList[i].inviteCode,
                                  inviteCodeURL : "http://" + location.hostname + ":" + location.port + "/#/subjectJoin/" + response.data.data.subjectDtoList[i].inviteCode,
                                  icon : '',
                                  idx : i
                              });
                          }


                      }
                      else{
                          vm.modals = true;
                          vm.responseMsg = response.data.message;
                      }
                  });
          },
          teacherSubjectsDetail(idx){
              console.log("click card" + idx);
              for(let i = 0; i < this.teacherSubjects.length; i++){
                  if(this.teacherSubjects[i].idx == idx){

                      //this.teacherSubjectsIcons[this.teacherSubjects[i].name] = 'ni ni-check-bold';
                      this.teacherSubjects[i].icon = 'ni ni-check-bold';
                      this.subjectPivot = this.teacherSubjects[i].subjectId;
                      this.subjectPivotName = this.teacherSubjects[i].subjectName;
                      this.subjectPivotNameEdit = this.teacherSubjects[i].subjectName;
                      this.subjectMasterId = this.teacherSubjects[i].userId;
                      this.nowUser = localStorage.getItem('userId');
                      this.getNotices();
                  }
                  else{
                      //this.teacherSubjectsIcons[this.teacherSubjects[i].name] = '';
                      this.teacherSubjects[i].icon = '';
                  }
                  console.log("for문 도는 중.. : " + this.teacherSubjects[i].icon + ", " + this.teacherSubjects[i].subjectId);
              }

              location.href="#subjectRoomJoinButton";
              //console.log("res2 : " + this.teacherSubjects[idx].icon);
          },
          copyToClipboard(url){
              let t = document.createElement("textarea");
              document.body.appendChild(t);
              t.value = url;
              t.select();
              document.execCommand('copy');
              document.body.removeChild(t);

              this.responseMsg = '링크가 복사 되었습니다!';
              this.modals = true;
          },

          /*
          *
          *
          * 과제공지의 메소드 영역
          *
          *
           */
          getNotices(){
              let vm = this;
              let apiParam = '';

              //로그인 유효성 체크
              if(localStorage.getItem('userId') == '' ||
                  localStorage.getItem('userId') == null){
                  this.responseMsg = "로그인이 필요한 서비스 입니다. 로그인 후 다시 시도해 주세요!";
                  this.modals = true;
                  return;
              }

              apiParam = 'userId=' + localStorage.getItem('userId');
              //전체 과목 조회
              if(this.subjectPivot != ''){
                  apiParam += '&subjectId=' + this.subjectPivot;
              }

              axios.get('/api/notices?' + apiParam)
                  .then(function(response){
                      if(response.data.statusCode == 'OK'){
                          vm.noticeDataSend = [];
                          for(let i = 0; i < response.data.data.length; i++){
                              vm.noticeDataSend.push({
                                  title : response.data.data[i].title,
                                  d_day : response.data.data[i].d_day,
                                  type :response.data.data[i].type,
                                  status : response.data.data[i].status,
                                  expiredAt : response.data.data[i].expiredAt
                              });
                          }
                      }
                      else{
                          vm.modals = true;
                          vm.responseMsg = response.data.message;
                      }
                  });
          }
      }
  };
</script>
<style></style>
