<template>
    <div>
        <base-header type="gradient-success" class="pb-6 pb-8 pt-5 pt-md-8">
            <div class="row">
                <div class="col-xl-12 col-lg-12">
                    <stats-card title="공개과제"
                                type="gradient-red"
                                sub-title="공개과제를 통해 지식을 공유해주시면 보상을 얻으실 수 있습니다"
                                class="mb-4 mb-xl-0"
                    >
                        <template slot="footer">
                        </template>
                    </stats-card>
                </div>
            </div>
        </base-header>


        <div class="container-fluid mt--7">
            <div class="row">
                <div class="col">
                    <template>
                        <div class="card shadow"
                             :class="type === 'dark' ? 'bg-default': ''">
                            <div class="card-header border-0"
                                 :class="type === 'dark' ? 'bg-transparent': ''">
                                <div class="row align-items-center">
                                    <div class="col">
                                        <h3 class="mb-0" :class="type === 'dark' ? 'text-white': ''">
                                            공개과제 리스트
                                        </h3>
                                    </div>
                                    <div class="col text-right">
                                        <!--base-button type="primary" size="sm" @click="clickChangeSunjectInfoButton()" v-if="subjectMasterId==nowUser">과목 정보수정</base-button>
                                        <base-button type="primary" size="sm" v-if="subjectMasterId==nowUser">과제 추가</base-button-->
                                    </div>
                                </div>
                            </div>

                            <div class="table-responsive" id="notiTable">
                                <base-table class="table align-items-center table-flush"
                                            :class="type === 'dark' ? 'table-dark': ''"
                                            :thead-classes="type === 'dark' ? 'thead-dark': 'thead-light'"
                                            tbody-classes="list"
                                            :data="tableData">
                                    <template slot="columns">
                                        <th>과목</th>
                                        <th>과제명</th>
                                        <th>내용</th>
                                        <th>마감 D-day</th>
                                        <th>좋아요</th>
                                    </template>

                                    <template slot-scope="{row}">
                                        <th scope="row">
                                            <div class="media align-items-center">
                                                <div class="media-body">
                                                  <a v-bind:href="'/#/notice/' + row.id"><span class="name mb-0 text-sm">{{row.subjectName}}</span></a>
                                                </div>
                                            </div>d
                                        </th>

                                        <td class="budget">
                                          <a v-bind:href="'/#/notice/' + row.id">{{row.title}}</a>
                                        </td>

                                        <td class="budget">
                                            {{row.content}}
                                        </td>

                                        <td class="budget">
                                            {{row.d_day}}
                                        </td>
                                        <td>
                                          <base-button
                                              v-if="row.status == true"
                                              type="primary" @click="down(row.id)"><i class="ni ni-like-2"></i> {{row.id + 11}}</base-button>
                                          <base-button
                                              v-else
                                              type="secondary" @click="up(row.id)"><i class="ni ni-like-2"></i> {{row.id + 10}}</base-button>
                                        </td>
                                    </template>

                                </base-table>
                            </div>

                            <div class="card-footer d-flex justify-content-end"
                                 :class="type === 'dark' ? 'bg-transparent': ''">
                                <base-pagination total="30"></base-pagination>
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

                        </div>
                    </template>
                </div>
            </div>
        </div>
        <br/>
        <br/>
        <br/>


        <modal :show.sync="modals">
            <template slot="header">
                <h5 class="modal-title" id="exampleModalLabel1">알림</h5>
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
                <base-button type="primary">과제방 생성</base-button>
                <base-button type="default" @click="modals2 = false">취소</base-button>
            </template>
        </modal>


    </div>
</template>
<script>
    import Vue from 'vue'
    import VueClipboard from 'vue-clipboard2'
    import BTooltipDirective from 'bootstrap-vue/esm/directives/tooltip'

    const axios = require('axios');

    Vue.use(VueClipboard)
    export default {
        components : {
        },
        directives: {
            'b-tooltip': BTooltipDirective
        },
        data() {
            return {
                likeSwitch : [
                    false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false,
                  false, false, false, false, false, false, false, false, false, false
                ],
                type: 'white',
                uName: '',
                modals: false,
                modals3: false,
                responseMsg: false,
                tableData: [
                    {
                        assignmentId: 'img/theme/bootstrap.jpg',
                        assignmentFileId: '2차 함수 응용',
                        subjectName: '50점',
                        noticeTile: '평가진행중',
                        feedback: 'warning',
                        score: 60
                    }
                ]
            }
        },
        created() {
            this.getTableInfo();
        },
        methods: {
            getTableInfo(){
                let vm = this;
                axios.get('/api/notices?type=OPEN')
                    .then(function(response){
                        if(response.data.statusCode == 'OK'){
                            vm.tableData = [];

                            vm.tableData = response.data.data;
                            console.log("테이블 수신완료");
                        }
                        else{
                            vm.modals = true;
                            vm.responseMsg = response.data.message;
                        }
                    });
            },
          up(id){
            console.log(id + "업 - 전" + this.likeSwitch[Number(id)]);
            this.likeSwitch[Number(id)] = true;
            console.log(id + "업 - 처리 후 " + this.likeSwitch[Number(id)]);
            for(let i = 0; i < this.tableData.length; i++){
              if(this.tableData[i].id  == id){
                this.tableData[i].status = true;
                console.log("status값 변경됨");
              }
            }
          },
          down(id){
            this.likeSwitch[Number(id)] = false;
            console.log(id + "다운" +  + this.likeSwitch[Number(id)]);
            for(let i = 0; i < this.tableData.length; i++){
              if(this.tableData[i].id  == id){
                this.tableData[i].status = false;
                console.log("status값 변경됨");
              }
            }
          }
        }
    };
</script>
<style></style>
