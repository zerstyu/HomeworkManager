<template>
    <div>
        <base-header type="gradient-success" class="pb-6 pb-8 pt-5 pt-md-8">
            <!-- Card stats -->
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <h3>내가 만든 과제방</h3>
                </div>
                <div class="col-xl-3 col-lg-6"
                     v-for="teacherSubject in teacherSubjects" v-bind:key="teacherSubject.id">
                    <stats-card v-bind:title="'방장 : ' + teacherSubject.userId"
                                v-bind:sub-title="teacherSubject.name"
                                @click="teacherSubjectsDetail()"
                                class="mb-4 mb-xl-0">

                        <!--template slot="footer">
                            <span class="text-success mr-2"><i class="fa fa-arrow-up"></i> 3.48%</span>
                            <span class="text-nowrap">Since last month</span>
                        </template-->
                    </stats-card>
                </div>

                <div class="col-xl-3 col-lg-6">
                    <stats-card title="남은과제 : 3/12"
                                icon="ni ni-settings"
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
              modals: false,
              modals2: false,
              createSubjectName: '',
              datasend : 'xxxx'
          }
      },
      created() {
          this.getSubjects();
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
                          vm.teacherSubjects = response.data.data;
                      }
                      else{
                          vm.modals = true;
                          vm.responseMsg = response.data.message;
                      }
                  });
          },
          teacherSubjectsDetail(){
              ProjectsTable.props.dataSendTest = '테스트2';
          }
      }
  };
</script>
<style></style>
