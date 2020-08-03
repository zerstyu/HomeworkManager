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
                            <span class="text-info mr-2"><i class="fa fa-arrow-up"></i>CODE </span>
                            <span class="text-nowrap">{{teacherSubject.inviteCode}} </span>
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
                <div class="col-xl-3 col-lg-6"
                     v-for="studentSubject in studentSubjects" v-bind:key="studentSubject.id"
                     @click="teacherSubjectsDetail(studentSubject.idx)">
                    <stats-card v-bind:title="studentSubject.userName + ' 선생님'"
                                v-bind:icon="studentSubject.icon"
                                v-bind:sub-title="studentSubject.subjectName"
                                class="mb-4 mb-xl-0">

                        <template slot="footer">
                            <!--span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 3.48%</span-->
                            <span class="text-nowrap">{{studentSubject.inviteCode}}</span>
                            <base-button type="info" size="sm" href="#" @click="copyToClipboard(studentSubject.inviteCodeURL)">URL 복사</base-button>
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

            <modal :show.sync="modals3">
                <template slot="header">
                    <h5 class="modal-title" id="exampleModalLabel3">과목 정보수정</h5>
                </template>
                <div>
                    <base-input placeholder="과목" v-model="subjectPivotNameEdit"></base-input>
                </div>
                <template slot="footer">
                    <base-button type="primary" @click="updateSubjectReq()">과목명 변경</base-button>
                    <base-button type="primary" @click="deleteSubjectReq()">과목 삭제</base-button>
                    <base-button type="default" @click="modals3 = false">취소</base-button>
                </template>
            </modal>

            <modal :show.sync="modals4">
                <template slot="header">
                    <h5 class="modal-title" id="exampleModalLabel4">과제 공지사항 추가</h5>
                </template>
                <div>
                    <h4>과제 공지 타이틀</h4>
                    <base-input placeholder="과목 공지 타이틀" v-model="notiEditTitle"></base-input>

                    <h4>과제 설명</h4>
                    <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" placeholder="이번 과제에 대해 설명해주세요"
                    v-model="notiEditContent"></textarea>
                    <br/>

                    <h4>마감일</h4>
                    <base-input placeholder="과제 마감일 (ex. 2020-01-01)" v-model="notiEditExpiredAt"></base-input>

                    <h4>이미지 첨부</h4>
                    <base-button size="sm" @click="createNotiFileMinus" type="primary">-</base-button>
                    {{notiEditFileLen}}
                    <base-button size="sm" @click="createNotiFilePlus()" type="primary">+</base-button>
                    <br/>
                    <div v-for="index in notiEditFileLen" :key="index">
                        <input type="file" id="files" name="notiEditFileInput" @change="getBase64(index)"/>
                    </div>
                    <br/>

                    <h4>과제 진행상태 설정</h4>
                    <base-radio name="PENDING" class="mb-3" v-model="notiEditStatus">
                        대기
                    </base-radio>
                    <base-radio name="PROGRESS" class="mb-3" v-model="notiEditStatus">
                        진행
                    </base-radio>
                    <base-radio name="COMPLETED" class="mb-3" v-model="notiEditStatus">
                        완료
                    </base-radio>
                    <base-radio name="CANCELED" class="mb-3" v-model="notiEditStatus">
                        취소
                    </base-radio>
                    <br/>

                    <h4>과제 유형</h4>
                    <base-radio name="OPEN" class="mb-4" v-model="notiEditType">
                        오픈과제
                    </base-radio>
                    <base-radio name="PRIVATE" class="mb-4" v-model="notiEditType">
                        프라이빗과제
                    </base-radio>

                </div>
                <template slot="footer">
                    <base-button type="primary" @click="createNoti()">과제 추가</base-button>
                    <base-button type="default" @click="modals4 = false">취소</base-button>
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
              modals: true, //알림
              modals2: false,
              modals3: false,
              modals4: false, //노티등록
              responseMsg: '데이터를 불러오고 있습니다.',
              createSubjectName: '',
              noticeDataSend : null,
              scrollOn : true,
              subjectMasterId : '-',

              subjectPivot: '',
              subjectPivotName: '전체 과제리스트',
              subjectPivotNameEdit: '',
              nowUser: '',

              /*noti 추가를 위한 data*/
              notiEditContent: '',
              notiEditExpiredAt: '',
              notiEditFileStringList: ["","","","","","","","","","",
                  "","","","","","","","","","",
                  "","","","","","","","","","",
                  "","","","","","","","","","",
                  "","","","","","","","","",""],
                notiEditFileLen: 1,
              notiEditStatus: '', //
              notiEditSubjectId: '',
              notiEditTitle: '',
              notiEditType: '',
              notiEditUserId: ''

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
          let vm = this;
          BUS.$on('subjectUpdate',function(data) {
              vm.modals3 = data;
              console.log("이벤트 수신 완료");
          });
          BUS.$on('goNotiDetail',function(data) {
              vm.goNotiDetail(data);
          });
          BUS.$on('createNoti',function(data) {
              vm.modals4 = data;
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
                          for(let i = 0; i < response.data.data[0].subjectDtoList.length; i++){
                              vm.teacherSubjects.push({
                                  userId : response.data.data[0].subjectDtoList[i].userId,
                                  userName : response.data.data[0].subjectDtoList[i].userName,
                                  subjectId :response.data.data[0].subjectDtoList[i].subjectId,
                                  subjectName :response.data.data[0].subjectDtoList[i].subjectName,
                                  inviteCode : response.data.data[0].subjectDtoList[i].inviteCode,
                                  inviteCodeURL : "http://" + location.hostname + ":" + location.port + "/#/subjectJoin/" + response.data.data[0].subjectDtoList[i].inviteCode,
                                  icon : '',
                                  idx : i
                              });
                          }
                          vm.studentSubjects = [];
                          for(let i = 0; i < response.data.data[0].joinSubjectDtoList.length; i++){
                              vm.teacherSubjects.push({
                                  userId : response.data.data[0].joinSubjectDtoList[i].userId,
                                  userName : response.data.data[0].joinSubjectDtoList[i].userName,
                                  subjectId :response.data.data[0].joinSubjectDtoList[i].subjectId,
                                  subjectName :response.data.data[0].joinSubjectDtoList[i].subjectName,
                                  inviteCode : response.data.data[0].joinSubjectDtoList[i].inviteCode,
                                  inviteCodeURL : "http://" + location.hostname + ":" + location.port + "/#/subjectJoin/" + response.data.data[0].joinSubjectDtoList[i].inviteCode,
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
          updateSubjectReq(){
              let vm = this;
              const axiosConfig = { headers:{ "Content-Type": "application/json"} };

              axios.put('/api/subjects/' + vm.subjectPivot,
                  '{' +
                  '"subjectName": "' + vm.subjectPivotNameEdit + '"' +
//                  '"subjectId": "' + vm.subjectPivot + '"' +
                  '}'
                  //vm.createSubjectName
                  //form
                  , axiosConfig)
                  .then(function(response){
                      if(response.data.statusCode == 'OK'){
                          vm.modals3 = false;
                          vm.modals = true;
                          vm.responseMsg = '성공하였습니다.';

                          vm.getSubjects();
                          vm.getNotices();
                      }
                      else{
                          vm.modals = true;
                          vm.responseMsg = response.data.message;
                      }
                  });
          },
          deleteSubjectReq(){
              alert("준비중 입니다.");
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
          getBase64(idx) {
              console.log("base64_1 변환 요청 idx : " + idx);
              let file = document.getElementsByName("notiEditFileInput")[idx-1].files[0];
              let idx2 = idx;
              let vm = this;


              let reader = new FileReader();
              reader.readAsDataURL(file);
              reader.onload = function () {
                  console.log("success!! base64 : " + reader.result);
                  vm.notiEditFileStringList[idx2] = reader.result;
              };
              reader.onerror = function (error) {
                  console.log('base 64 Error.... : ', error);
              };
          },
          getBase64_2(idx) {
              console.log("base64 변환 요청 idx : " + idx);
              //var file = document.querySelector('#myFile');
              let file = document.getElementsByName("notiEditFileInput")[idx-1];
              let result;

            // 정상 로드시 result에 인코딩 값을 저장하기
              let idx2 = idx;
              let vm = this;
              let reader = new FileReader(file);
              reader.onload = function() {
                  result = reader.result;
                  console.log("base64 입니다 : " + result);
                  vm.notiEditFileStringList[idx2] = result;
              }

                // 실패할 경우 에러 출력하기
              reader.onerror = function (error) {
                  console.log('base64 Error : ' + error);
              };
          },
          goNotiDetail(id){
              location.href="/#/notice/" + id;
          },
          createNoti(){
              this.modals4 = false;
              let vm = this;
              let base64Str = '';
              let base64Flag = false;
//              let fileInput = document.getElementsByName("notiEditFileInput");

              for(let i = 1; i <= this.notiEditFileLen; i++){
//              for(let i = 0; i < fileInput.length; i++){
                  base64Flag = true;
                  //let file = document.querySelector('#files > input[type="file"]').files[0];
                  //var array_fruits = document.getElementsByName("fruits");
                  base64Str += '"';
                  //base64Str += this.getBase64(file);
                  base64Str += this.notiEditFileStringList[i];
                  base64Str += '",';
              }
              if(base64Flag == true){
                  base64Str = base64Str.substr(0, base64Str.length -1);
              }
              //let form = new FormData();
              //form.append('subjectName', this.createSubjectName);
              //form.append('userId', localStorage.getItem('userId'));
              const axiosConfig = { headers:{ "Content-Type": "application/json"} };

              axios.post('/api/notices',
                  '{' +
                  '"content": "' + vm.notiEditContent.replace(/(?:\r\n|\r|\n)/g, '<br/>') + '",' +
                  '"expiredAt": "' + vm.notiEditExpiredAt + '",' +
                  '"noticeFileStringList": [' + base64Str + '],' +
                  '"status": "' + vm.notiEditStatus + '",' +
                  '"subjectId": "' + vm.subjectPivot + '",' +
                  '"title": "' + vm.notiEditTitle + '",' +
                  '"type": "' + vm.notiEditType + '",' +
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
                          vm.getNotices();
                      }
                      else{
                          vm.modals = true;
                          vm.responseMsg = response.data.message;
                      }
                  });
          },
          createNotiFilePlus(){
              if(this.notiEditFileLen >= 50) {
                  this.notiEditFileLen += 50;
                  return;
              }
              this.notiEditFileLen += 1;
          },
          createNotiFileMinus(){
              if(this.notiEditFileLen <= 0){
                  this.notiEditFileLen = 0;
                  return;
              }
              this.notiEditFileLen -= 1;
          },
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
                                  id : response.data.data[i].id,
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
