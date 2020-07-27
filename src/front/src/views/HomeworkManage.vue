<template>
    <div>
        <base-header type="gradient-success" class="pb-6 pb-8 pt-5 pt-md-8">
            <!-- Card stats -->
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <h3>내가 만든 과제방</h3>
                </div>
                <div class="col-xl-3 col-lg-6"
                     v-for="teacherSubject in teacherSubjects" v-bind:key="teacherSubject.id"
                     @click="teacherSubjectsDetail(teacherSubject.idx)">
                    <stats-card v-bind:title="'방장 : ' + teacherSubject.userId"
                                v-bind:icon="teacherSubject.icon"
                                v-bind:sub-title="teacherSubject.name"
                                class="mb-4 mb-xl-0">

                        <!--template slot="footer">
                            <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 3.48%</span>
                            <span class="text-nowrap">Since last month</span>
                        </template-->
                    </stats-card>
                </div>

                <div class="col-xl-3 col-lg-6">
                    <stats-card title="남은과제 : 3/12"
                                icon="ni ni-check-bold"
                                sub-title="1학년 1반"
                                class="mb-4 mb-xl-0">

                        <template slot="footer">
                            <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 3.48%</span>
                            <span class="text-nowrap">Since last month</span>
                        </template>
                    </stats-card>
                </div>

                <div class="col-xl-12 col-lg-12">
                    <br/>
                    <base-button type="primary" icon="ni ni-bag-17" v-on:click="modals2 = true">과제방 만들기</base-button>
                </div>


                <div class="col-xl-12 col-lg-12">
                    <br/><br/>
                    <h3>내가 참여한 과제방</h3>
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
                    <base-button type="primary" icon="ni ni-bag-17">과제방 가입하기</base-button>
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
                    <projects-table title="1학년 1반 과제리스트" v-bind:data-send-test="datasend"></projects-table>
                </div>
            </div>
            <!--div class="row mt-5">
                <div class="col">
                    <projects-table type="dark" title="Dark Table"></projects-table>
                </div>
            </div-->
        </div>
        <div v-if="scrollOn" class="container-fluid mt--7">
            <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
        </div>

    </div>

</template>
<script>
  import ProjectsTable from './Tables/ProjectsTable'
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
              responseMsg: '데이터를 불러오고 있습니다.',
              createSubjectName: '',
              datasend : 'xxxx',
              scrollOn : true
          }
      },
      created() {
          this.getSubjects();
          //let vm = this;
          //vm.modals = false;
/*
          setTimeout(function() {
              console.log('Works!');
              vm.scrollOn = false;
              vm.modals = false;
          }, 500);
*/
      },
      mounted() {
          this.modals = false;
      },
      methods: {
          createSubject(){
              this.modals2 = false;
              let vm = this;

              const axiosConfig = { headers:{ "Content-Type": "application/json"} };

              axios.post('/api/subjects',
                  '{' +
                  '"changeName": "",' +
                  '"name": "' + vm.createSubjectName + '",' +
                  '"userId": "' + localStorage.getItem('userId') + '"' +
                  '}'
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

              axios.get('/api/subjects/' + localStorage.getItem('userId'))
                  .then(function(response){
                      if(response.data.statusCode == 'OK'){
                          /*
                          vm.teacherSubjectsIcons = [];
                          for(let i = 0; i < response.data.data.length; i++){
                              vm.teacherSubjectsIcons[response.data.data[i].name] = '';
                              console.log("아이콘배열초기화 : " + vm.teacherSubjectsIcons[response.data.data[i].name] + " , " + response.data.data[i].name);
                          }
                          */

                          //vm.teacherSubjects = response.data.data;

                          vm.teacherSubjects = [];
                          for(let i = 0; i < response.data.data.length; i++){
                              vm.teacherSubjects.push({
                                  userId : response.data.data[i].userId,
                                  name :response.data.data[i].name,
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
                  }
                  else{
                      //this.teacherSubjectsIcons[this.teacherSubjects[i].name] = '';
                      this.teacherSubjects[i].icon = '';
                  }
                  console.log("for문 도는 중.. : " + this.teacherSubjects[i].icon + ", " + this.teacherSubjects[i].name);
              }
              //console.log("res2 : " + this.teacherSubjects[idx].icon);

          }
      }
  };
</script>
<style></style>
