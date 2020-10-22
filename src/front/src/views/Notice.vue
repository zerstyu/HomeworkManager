<template>
    <div>
        <base-header type="gradient-success" class="pb-6 pb-8 pt-5 pt-md-8">
            <!-- Card stats -->
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <h3>{{notiUserName}} 선생님이 출제한 {{notiSubjectName}} 숙제 입니다.</h3>
                </div>
                <div class="col-xl-12 col-lg-12">
                    <stats-card v-bind:title="notiSubjectName"
                                v-bind:sub-title="notiTitle"
                                class="mb-4 mb-xl-0">
                        <h3>{{notiTitle}}</h3>
                        <badge pill type="info">{{notiDday}}</badge>
                        <badge pill type="info">{{notiUserName}} 선생님</badge>

                        <badge pill type="info" v-if="notiType == 'OPEN'">공개숙제</badge>
                        <badge pill type="info" v-else>비공개숙제</badge>

                        <badge pill type="warning" v-if="notiStatus == 'PENDING'">대기</badge>
                        <badge pill type="info" v-else-if="notiStatus == 'PROGRESS'">진행중</badge>
                        <badge pill type="info" v-else-if="notiStatus == 'GRADING'">채점중</badge>
                        <badge pill type="success" v-else-if="notiStatus == 'COMPLETED'">완료</badge>
                        <badge pill type="danger" v-else>취소됨</badge>
                        <br/>
                        <br/>
                        <p v-html="notiContent"></p>
                        <hr/>
                        <div v-for="item in noticeFileResponseList" v-bind:key="item.id">
                          <div class="jb-wrap">
                            <div class="jb-image">
                              <img style="max-width:100%; min-height: 150px" v-bind:src="item.fileString"/>
                            </div>
                            <div class="jb-text">
                              <p>[{{nowUserName}}] 모두의숙제의 모든 콘텐츠는<br/>
                                저작권법의 보호를 받으며 무단 복사, 배포 등을 금합니다.</p>
                            </div>
                          </div>
                          <br/>
                          <center><a v-on:click="downloadImg(item.fileString)">[다운로드]</a></center>



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
                    <base-button type="primary" icon="ni ni-bag-17" v-if="isNotiMaster == true" @click="openUpdateModal()">숙제 출제 수정</base-button>
                    <base-button type="primary" icon="ni ni-bag-17" @click="modals5 = true">숙제 제출하기</base-button>
                    <!--base-button type="primary" icon="ni ni-bag-17" v-if="isNotiMaster == false && createAssignIsNew == true" @click="modals5 = true">숙제 제출하기</base-button-->
                    <!--base-button type="primary" icon="ni ni-bag-17"  @click="modals5 = true">숙제 제출하기</base-button-->

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
                    <h5 class="modal-title" id="exampleModalLabel2">숙제방 만들기</h5>
                </template>
                <div>
                    <base-input placeholder="숙제명을 입력해주세요" v-model="createSubjectName"></base-input>
                </div>
                <template slot="footer">
                    <base-button type="primary" @click="createSubject()">숙제방 생성</base-button>
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
                    <h5 class="modal-title" id="exampleModalLabel4">숙제 출제</h5>
                </template>
                <div>
                    <h4>숙제 타이틀</h4>
                    <base-input placeholder="숙제 타이틀" v-model="notiEditTitle"></base-input>

                    <h4>숙제 설명</h4>
                    <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" placeholder="이번 숙제에 대해 설명해주세요"
                              v-model="notiEditContent"></textarea>
                    <br/>

                    <h4>마감일</h4>
                    <base-input placeholder="숙제 마감일 (ex. 2020-01-01)" v-model="notiEditExpiredAt"></base-input>

                    <h4>파일 첨부</h4>
                    <base-button size="sm" @click="createNotiFileMinus" type="primary">-</base-button>
                    {{notiEditFileLen}}
                    <base-button size="sm" @click="createNotiFilePlus()" type="primary">+</base-button>
                    <br/>
                    <div v-for="index in notiEditFileLen" :key="index">
                        <input type="file" id="files" name="notiEditFileInput" @change="getBase64(index)"/>
                    </div>
                    <br/>

                    <h4>숙제 진행상태 설정</h4>
                    <base-radio name="PENDING" class="mb-3" v-model="notiEditStatus">
                        대기
                    </base-radio>
                    <base-radio name="PROGRESS" class="mb-3" v-model="notiEditStatus">
                        진행
                    </base-radio>
                    <base-radio name="GRADING" class="mb-3" v-model="notiEditStatus">
                        채점중
                    </base-radio>
                    <base-radio name="COMPLETED" class="mb-3" v-model="notiEditStatus">
                        완료
                    </base-radio>
                    <base-radio name="CANCELED" class="mb-3" v-model="notiEditStatus">
                        취소
                    </base-radio>
                    <br/>

                </div>
                <template slot="footer">
                    <base-button type="primary" @click="updateNoti()">숙제 출제 수정</base-button>
                    <base-button type="default" @click="modals4 = false">취소</base-button>
                </template>
            </modal>


            <modal :show.sync="modals5">
                <template slot="header">
                    <h5 class="modal-title" id="exampleModalLabel5">숙제 제출</h5>
                </template>
                <div>

                    <h4>숙제 노트</h4>
                    <textarea class="form-control" id="exampleFormControlTextarea111" rows="3" placeholder="완료한 숙제에 대해 설명하세요"
                              v-model="createAssignNote"></textarea>
                    <br/>

                    <h4>숙제자료 첨부</h4>
                    <base-button size="sm" @click="createAssignFileMinus()" type="primary">-</base-button>
                    {{createAssignmentFileLen}}
                    <base-button size="sm" @click="createAssignFilePlus()" type="primary">+</base-button>
                    <br/>
                    <div v-for="index in createAssignmentFileLen" :key="index">
                        <input type="file" id="files2" name="createAssignFileInput" @change="getBase64_assign(index)"/>
                    </div>
                    <br/>

                    <h4>숙제 유형</h4>
                    <base-radio name="OPEN" class="mb-5" v-model="createAssignIsOpen">
                        공개 숙제
                    </base-radio>
                    <base-radio name="PRIVATE" class="mb-5" v-model="createAssignIsOpen">
                        비공개 숙제
                    </base-radio>

                </div>
                <template slot="footer">
                    <base-button type="primary" @click="homeworkSubmit()">숙제 제출</base-button>
                    <base-button type="default" @click="modals5 = false">취소</base-button>
                </template>
            </modal>

          <modal :show.sync="modals6">
            <template slot="header">
              <h5 class="modal-title" id="exampleModalLabel6">알림</h5>
            </template>
            <div>
              숙제를 제출하였습니다 !<br/>
              <br/>
              <hr>
              <br/>
              열심히 숙제하고 멋진 선물도 받아보세요!<br/>
              <br/>
              <table>
                <tr>
                  <td width="150px"><center><b>숙제 10번</b></center></td>
                  <td width="150px"><center><b>숙제 50번</b></center></td>
                  <td width="150px"><center><b>숙제 100번 제출!</b></center></td>
                </tr>
                <tr>
                  <td><center><img src="/img/reward1.jpg" style="max-width:120px"/><br>우리나라 스티커</center></td>
                  <td><center><img src="/img/reward2.jpg" style="max-width:120px"/><br>필기도구 세트</center></td>
                  <td><center><img src="/img/reward3.jpg" style="max-width:120px"/><br>미니드론</center></td>
                </tr>
              </table>
            </div>
            <template slot="footer">
              <base-button type="secondary" @click="modals6 = false">확인</base-button>
              <!--base-button type="primary">Save changes</base-button-->
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


        <!--Charts-->
        <div class="container-fluid mt--7">
            <div class="row" style="margin-top: 150px;">
                <div class="col-xl-8 mb-5 mb-xl-0">
                    <card type="default" header-classes="bg-transparent">
                        <div slot="header" class="row align-items-center">
                            <div class="col">
                                <h6 class="text-light text-uppercase ls-1 mb-1">통계자료</h6>
                                <h5 class="h3 text-white mb-0">숙제별 평균점수</h5>
                            </div>
                        </div>
                        <line-chart
                                :height="350"
                                ref="bigChart"
                                :chart-data="bigLineChart.chartData"
                                :extra-options="bigLineChart.extraOptions"
                        >
                        </line-chart>

                    </card>
                </div>

                <div class="col-xl-4">
                    <card header-classes="bg-transparent">
                        <div slot="header" class="row align-items-center">
                            <div class="col">
                                <h6 class="text-uppercase text-muted ls-1 mb-1">학생 통계</h6>
                                <h5 class="h3 mb-0">학생별 평균 순위</h5>
                            </div>
                        </div>

                        <bar-chart
                                :height="350"
                                ref="barChart"
                                :chart-data="redBarChart.chartData"
                        >
                        </bar-chart>
                    </card>
                </div>
            </div>
        </div>
            <!-- End charts-->


    </div>

</template>
<script>
  import ProjectsTable from './Tables/AssignmentTable'
  import {BUS} from "./EventBus";
  // Charts
  import * as chartConfigs from '../components/Charts/config';
  import LineChart from '../components/Charts/LineChart';
  import BarChart from '../components/Charts/BarChart';
  //import {BUS} from "./EventBus";

  const axios = require('axios');

  export default {
    name: 'tables',
    components: {
      ProjectsTable,
      LineChart,
      BarChart
    },
      data() {
          return {
              bigLineChart: {//bigLineChart.allData.allData
                  allData: [
                      []
                  ],
                  activeIndex: 0,
                  chartData: {
                      datasets: [],
                      labels: [],
                  },
                  extraOptions: chartConfigs.blueChartOptions,
                  labels: []
              },
              redBarChart: {
                  chartData: {
                      labels: [],
                      datasets: [{
                          label: '총점',
                          data: []
                      }]
                  }
              },

              isNotiMaster: false,
              nowUser: '',
              nowUserName: localStorage.getItem('userName'),

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
              notiEditType: 'OPEN',
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

              responseMsg: '데이터를 기다리는 중입니다.',
              modals: true,
              modals2: false,
              modals4: false,
              modals5: false,
              modals6 : false
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
          this.initBigChart(0);

      },
      methods: {
          downloadImg(link){
            //console.log("다운로드 링크 : " + link);
            //window.open(link, '_blank');

            /*
            let w = window.open('about:blank');

            setTimeout(function(){ //FireFox seems to require a setTimeout for this to work.
              w.document.body.appendChild(w.document.createElement('iframe'))
                  .src = link;
            }, 0);
             */

            var a = document.createElement("a"); //Create <a>
            a.href = link; //Image Base64 Goes here
            a.download = "homework.png"; //File name Here
            a.click(); //Downloaded file

          },
          changeRedChart(nameArr, scoreArr){
              let tmp = {
                  chartData: {
                      labels: nameArr,
                          datasets: [{
                          label: '총점',
                          data: scoreArr
                      }]
                  }
              }
              this.redBarChart = tmp;
          },
          initBigChart(index) {
              let chartData = {
                  datasets: [
                      {
                          label: '숙제평균',
                          data: this.bigLineChart.allData[index]
                      }
                  ],
                  //labels: ['May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                  labels: this.bigLineChart.labels
              };
              this.bigLineChart.chartData = chartData;
              console.log(this.bigLineChart.chartData);
              this.bigLineChart.activeIndex = index;
          },
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
                          vm.modals6 = true;
                          //vm.responseMsg = '성공하였습니다.';
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
          /** 통계 **/
          getStaticsAverages(){
              let vm = this;

              axios.get('/api/statistics/subjects/averages?id=' + this.notiSubjectId)//this.$route.params.notiId
                  .then(function(response){
                      if(response.data.statusCode == 'OK'){
                          vm.bigLineChart.allData[0] = [];
                          vm.bigLineChart.labels = [];

                          for(let i = 0; i < response.data.data[0].statisticsDtoList.length; i++){
                              vm.bigLineChart.allData[0].push(response.data.data[0].statisticsDtoList[i].averageScore);
                              vm.bigLineChart.labels.push(response.data.data[0].statisticsDtoList[i].title);
                              console.log("에버리지 : " + vm.bigLineChart.allData[0][i]);

                              if(i == response.data.data[0].statisticsDtoList.length - 1){
                                  vm.bigLineChart.allData[0].push(response.data.data[0].statisticsDtoList[i].averageScore);
                              }
                          }
                          console.log(vm.bigLineChart.allData[0]);
                          console.log(vm.bigLineChart.labels);


                          vm.initBigChart(0);
                      }
                      else{
                          vm.modals = true;
                          vm.responseMsg = response.data.message;
                      }
                  });
          },
          getStaticsTotalScore(){
              let vm = this;
              axios.get('/api/statistics/subjects/total-averages?id=' + this.notiSubjectId)//this.$route.params.notiId
                  .then(function(response){
                      if(response.data.statusCode == 'OK'){
                          vm.redBarChart.chartData.labels = [];
                          vm.redBarChart.chartData.datasets[0].data = [];

                          let nameAr = [];
                          let scoreAr = [];

                          for(let i = 0; i < response.data.data[0].statisticsDtoList.length; i++){
                              nameAr.push(response.data.data[0].statisticsDtoList[i].userName);
                              scoreAr.push(response.data.data[0].statisticsDtoList[i].averageScore);
                              //vm.redBarChart.chartData.labels.push(response.data.data[0].statisticsDtoList[i].userName);
                              //vm.redBarChart.chartData.datasets[0].data.push(response.data.data[0].statisticsDtoList[i].totalScore);
                          }
                          vm.changeRedChart(nameAr, scoreAr);
                          vm.modals = false;
                      }
                      else{
                          vm.modals = true;
                          vm.responseMsg = response.data.message;
                      }
                  });
          },
          /*
          *
          *
          * 숙제공지의 메소드 영역
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
                          vm.getStaticsAverages();
                          vm.getStaticsTotalScore();
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
//                  '"type": "OPEN",' +
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
                          vm.assignments = [];
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

  function click()
  {
    if ((event.button==2) || (event.button==2)) {
      alert('마우스 우클릭은 사용할 수 없습니다.');
    }
  }
  document.onmousedown=click;

</script>
<style></style>
