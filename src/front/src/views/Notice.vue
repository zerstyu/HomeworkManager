<template>
    <div>
        <base-header type="gradient-success" class="pb-6 pb-8 pt-5 pt-md-8">
            <!-- Card stats -->
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <h3>{{notiUserName}} 선생님의 {{notiSubjectName}} 과제공지 입니다.</h3>
                </div>
                <div class="col-xl-12 col-lg-12">
                    <stats-card v-bind:title="notiSubjectName"
                                v-bind:sub-title="notiTitle"
                                class="mb-4 mb-xl-0">
                        <h3>{{notiTitle}}</h3>
                        <badge pill type="info">{{notiDday}}</badge>
                        <badge pill type="info">{{notiUserName}} 선생님</badge>

                        <badge pill type="info" v-if="notiType == 'OPEN'">오픈과제</badge>
                        <badge pill type="info" v-else>프라이빗과제</badge>

                        <badge pill type="warning" v-if="notiStatus == 'PENDING'">대기</badge>
                        <badge pill type="info" v-else-if="notiStatus == 'PROGRESS'">진행중</badge>
                        <badge pill type="success" v-else-if="notiStatus == 'COMPLETED'">완료</badge>
                        <badge pill type="danger" v-else>취소됨</badge>
                        <br/>
                        <br/>
                        <p v-html="notiContent"></p>
                        <hr/>
                        <div v-for="item in noticeFileResponseList" v-bind:key="item.id">
                            <img style="width:auto;" v-bind:src="item.fileString"/>
                            <br/>
                        </div>
                        <template slot="footer">
                            <span class="text-nowrap">기한 {{notiExpiredAt}}까지</span>
                            <!--base-button type="info" size="sm" @click="copyToClipboard('복사잘돼나')">복사</base-button-->
                        </template>
                    </stats-card>
                </div>

                <div class="col-xl-12 col-lg-12">
                    <br/>
                    <base-button type="primary" icon="ni ni-bag-17" v-if="isNotiMaster == true" @click="openUpdateModal()">공지글 수정</base-button>
                    <base-button type="primary" icon="ni ni-bag-17" v-if="isNotiMaster == false && createAssignIsNew == true" @click="modals5 = true">과제 제출하기</base-button>
                    <base-button type="primary" icon="ni ni-bag-17"  @click="modals5 = true">테스트 과제 제출하기</base-button>

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
                    <base-button type="primary" @click="updateNoti()">과제공지 수정</base-button>
                    <base-button type="default" @click="modals4 = false">취소</base-button>
                </template>
            </modal>


            <modal :show.sync="modals5">
                <template slot="header">
                    <h5 class="modal-title" id="exampleModalLabel5">과제 제출</h5>
                </template>
                <div>

                    <h4>과제 노트</h4>
                    <textarea class="form-control" id="exampleFormControlTextarea111" rows="3" placeholder="완료한 과제에 대해 설명하세요"
                              v-model="createAssignNote"></textarea>
                    <br/>

                    <h4>과제자료 첨부</h4>
                    <base-button size="sm" @click="createAssignFileMinus()" type="primary">-</base-button>
                    {{createAssignmentFileLen}}
                    <base-button size="sm" @click="createAssignFilePlus()" type="primary">+</base-button>
                    <br/>
                    <div v-for="index in createAssignmentFileLen" :key="index">
                        <input type="file" id="files2" name="createAssignFileInput" @change="getBase64_assign(index)"/>
                    </div>
                    <br/>

                    <h4>과제 유형</h4>
                    <base-radio name="OPEN" class="mb-5" v-model="createAssignIsOpen">
                        오픈과제
                    </base-radio>
                    <base-radio name="PRIVATE" class="mb-5" v-model="createAssignIsOpen">
                        프라이빗과제
                    </base-radio>

                </div>
                <template slot="footer">
                    <base-button type="primary" @click="homeworkSubmit()">과제 제출</base-button>
                    <base-button type="default" @click="modals5 = false">취소</base-button>
                </template>
            </modal>


        </base-header>

        <div class="container-fluid mt--7">
            <div class="row">
                <div class="col">
                    <projects-table
                            v-bind:assignments-data="assignments"
                            v-bind:is-noti-master="isNotiMaster"></projects-table>
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
  import ProjectsTable from './Tables/AssignmentTable'
  import {BUS} from "./EventBus";
  //import {BUS} from "./EventBus";

  const axios = require('axios');

  export default {
    name: 'tables',
    components: {
      ProjectsTable
    },
      data() {
          return {
              isNotiMaster: false,
              nowUser: '',

              notiTitle: '',
              notiContent: '',
              notiDday: '',
              notiType: '',
              notiStatus: '',
              notiExpiredAt: '',
              notiUserId: '',
              notiUserName: '',
              notiSubjectName: '',
              notiSubjectId: '',
              noticeFileResponseList: [],


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
              notiEditUserId: '',

              assignments: [],


              createAssignIsNew: false,
              createAssignIsOpen: 'OPEN',
              createAssignNote: '',
              //createAssignNoticeId:
              createAssignScore: 0,
              //createAssignSubjectId
              //createAssignUserId
              createAssignmentFileDtoList: ["","","","","","","","","","",
                  "","","","","","","","","","",
                  "","","","","","","","","","",
                  "","","","","","","","","","",
                  "","","","","","","","","",""],
              createAssignmentFileLen: 1,



              modals: true,
              modals2: false,
              modals4: false,
              modals5: false
          }
      },
      created() {
          let vm = this;
          this.getNotices();

          BUS.$on('assignmentDetail',function(data) {
              //vm.goNotiDetail(data);
              //location.href="/#/notice/" + id;
              vm.goAssignDetail(data);
          });
          BUS.$on('confirm',function(data) {
              //location.href="/#/notice/" + data;
              vm.goConfirm(data)
          });
      },
      mounted() {
          this.modals = false;
          this.nowUser = localStorage.getItem('userId');
      },
      methods: {
          homeworkSubmit(){
              this.modals5 = false;
              let vm = this;
              let isOpenOrClose = false;
              if(this.createAssignIsOpen == 'OPEN') {
                  isOpenOrClose = true;
              }
              const axiosConfig = { headers:{ "Content-Type": "application/json"} };

              axios.post('/api/assignments/',
                  '{' +
                  '"note": "' + vm.createAssignNote.replace(/(?:\r\n|\r|\n)/g, '<br/>') + '",' +
                  '"isOpen": "' + isOpenOrClose + '",' +
                  '"noticeId": "' + vm.$route.params.notiId + '",' +
                  '"score": "0",' +
                  '"subjectId": "' + vm.notiSubjectId + '",' +
                  '"userId": "' + localStorage.getItem('userId') + '"' +
                  '}'
                  , axiosConfig)
                  .then(function(response){
                      if(response.data.statusCode == 'OK'){

                          vm.homeworkFileSubmit(response.data.data.id);
                      }
                      else{
                          vm.modals = true;
                          vm.responseMsg = response.data.message;
                      }
                  });
          },
          homeworkFileSubmit(assId){
              let vm = this;
              let base64Str = '';
              let base64Flag = false;

              for(let i = 1; i <= this.createAssignmentFileLen; i++){
                  base64Flag = true;
                  base64Str += '{ "assignmentId" : "' + assId + '",';
                  base64Str += '  "fileString" : "' + this.createAssignmentFileDtoList[i] + '",';
                  base64Str += '  "type" : "ORIGINAL",';
                  base64Str += '  "userId" : "' + localStorage.getItem('userId') + '"},';
              }
              if(base64Flag == true){
                  base64Str = base64Str.substr(0, base64Str.length -1);
              }
              const axiosConfig = { headers:{ "Content-Type": "application/json"} };

              axios.post('/api/assignment_files',
                  '[' + base64Str + ']'
                  , axiosConfig)
                  .then(function(response){
                      if(response.data.statusCode == 'OK'){
                          vm.modals = true;
                          vm.responseMsg = '성공하였습니다.';
                          vm.getAssignments();
                      }
                      else{
                          vm.modals = true;
                          vm.responseMsg = response.data.message;
                      }
                  });
          },
          goAssignDetail(id){
              location.href="/#/homeworkDetail/" + id;
          },
          goConfirm(id){
              location.href="/#/homeworkEdit/" + id;
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

              //로그인 유효성 체크
              if(localStorage.getItem('userId') == '' ||
                  localStorage.getItem('userId') == null){
                  this.responseMsg = "로그인이 필요한 서비스 입니다. 로그인 후 다시 시도해 주세요!";
                  this.modals = true;
                  return;
              }

              axios.get('/api/notices/' + this.$route.params.notiId)
                  .then(function(response){
                      if(response.data.statusCode == 'OK'){
                          vm.notiTitle = response.data.data.title;
                          vm.notiContent = response.data.data.content;
                          vm.notiDday = response.data.data.d_day;
                          vm.notiType = response.data.data.type;
                          vm.notiStatus = response.data.data.status;
                          vm.notiExpiredAt = response.data.data.expiredAt;
                          vm.notiUserId = response.data.data.userId;
                          vm.notiUserName = response.data.data.userName;
                          vm.notiSubjectName = response.data.data.subjectName;
                          vm.notiSubjectId = response.data.data.subjectId;
                          vm.noticeFileResponseList = response.data.data.noticeFileResponseList;

                          if(vm.notiUserId == localStorage.getItem('userId')){
                              vm.isNotiMaster = true;
                          }

                          vm.getAssignments();
                      }
                      else{
                          vm.modals = true;
                          vm.responseMsg = response.data.message;
                      }
                  });
          },
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
          getBase64_assign(idx) {
              console.log("base64_1 변환 요청 idx : " + idx);
              let file = document.getElementsByName("createAssignFileInput")[idx-1].files[0];
              let idx2 = idx;
              let vm = this;


              let reader = new FileReader();
              reader.readAsDataURL(file);
              reader.onload = function () {
                  console.log("success!! base64_assign : " + reader.result);
                  vm.createAssignmentFileDtoList[idx2] = reader.result;
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
          updateNoti(){
              this.modals4 = false;
              let vm = this;
              let base64Str = '';
              let base64Flag = false;

              for(let i = 1; i <= this.notiEditFileLen; i++){
                  base64Flag = true;
                  base64Str += '"';
                  base64Str += this.notiEditFileStringList[i];
                  base64Str += '",';
              }
              if(base64Flag == true){
                  base64Str = base64Str.substr(0, base64Str.length -1);
              }
              const axiosConfig = { headers:{ "Content-Type": "application/json"} };

              axios.put('/api/notices/' + this.$route.params.notiId,
                  '{' +
                  '"content": "' + vm.notiEditContent.replace(/(?:\r\n|\r|\n)/g, '<br/>') + '",' +
                  '"expiredAt": "' + vm.notiEditExpiredAt + '",' +
                  '"noticeFileStringList": [' + base64Str + '],' +
                  '"status": "' + vm.notiEditStatus + '",' +
                  '"subjectId": "' + vm.notiEditSubjectId + '",' +
                  '"title": "' + vm.notiEditTitle + '",' +
                  '"type": "' + vm.notiEditType + '",' +
                  '"userId": "' + localStorage.getItem('userId') + '"' +
                  '}'
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
          createAssignFilePlus(){
              if(this.createAssignmentFileLen >= 50) {
                  this.createAssignmentFileLen += 50;
                  return;
              }
              this.createAssignmentFileLen += 1;
          },
          createAssignFileMinus(){
              if(this.createAssignmentFileLen <= 0){
                  this.createAssignmentFileLen = 0;
                  return;
              }
              this.createAssignmentFileLen -= 1;
          },
          openUpdateModal(){

                  /*noti 수정 위한 data*/
                  this.notiEditContent = this.notiContent;
                  this.notiEditExpiredAt = this.notiExpiredAt;
                  for(let i = 0; i < this.noticeFileResponseList.length; i++){
                      this.notiEditFileStringList[i + 1] = this.noticeFileResponseList[i];
                  }
                  this.notiEditFileLen = this.noticeFileResponseList.length;
                  this.notiEditStatus = this.notiStatus;
                  this.notiEditSubjectId = this.notiSubjectId;
                  this.notiEditTitle = this.notiTitle;
                  this.notiEditType = this.notiType;
                  this.notiEditUserId = this.notiUserId;

              this.modals4 = true;
          },


          getAssignments(){
              let vm = this;

              //로그인 유효성 체크
              if(localStorage.getItem('userId') == '' ||
                  localStorage.getItem('userId') == null){
                  this.responseMsg = "로그인이 필요한 서비스 입니다. 로그인 후 다시 시도해 주세요!";
                  this.modals = true;
                  return;
              }

              let userIdParam = '';
              if(this.isNotiMaster == false){
                  userIdParam = '&userId=' + this.nowUser;
              }

              axios.get('/api/assignments?noticeId=' + this.$route.params.notiId + userIdParam)
                  .then(function(response){
                      if(response.data.statusCode == 'OK'){
                          for(let i = 0; i < response.data.data.length; i++){
                              vm.assignments.push({
                                  id : response.data.data[i].id,
                                  createdAt : response.data.data[i].createdAt,
                                  updatedAt : response.data.data[i].updatedAt,
                                  userId :response.data.data[i].user.id,
                                  userName : response.data.data[i].user.name,
                                  feedback : response.data.data[i].feedback,
                                  score : response.data.data[i].score,
                                  isOpen : response.data.data[i].isOpen,
                                  note : response.data.data[i].note
                              });
                          }
                          if(response.data.data.length == 0){
                              vm.createAssignIsNew = true;
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
