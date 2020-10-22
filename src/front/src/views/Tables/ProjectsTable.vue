<template>
  <div class="card shadow"
       :class="type === 'dark' ? 'bg-default': ''">
    <div class="card-header border-0"
         :class="type === 'dark' ? 'bg-transparent': ''">
      <div class="row align-items-center">
        <div class="col">
          <h3 class="mb-0" :class="type === 'dark' ? 'text-white': ''">
            {{title}}
          </h3>
        </div>
        <div class="col text-right">
          <base-button type="primary" size="sm" @click="clickChangeSunjectInfoButton()" v-if="subjectMasterId==nowUser">과목 정보수정</base-button>
          <base-button type="primary" size="sm" @click="clickAddNotiButton()" v-if="subjectMasterId==nowUser">숙제 추가</base-button>
        </div>
      </div>
    </div>

    <div class="table-responsive" id="notiTable">
      <base-table class="table align-items-center table-flush"
                  :class="type === 'dark' ? 'table-dark': ''"
                  :thead-classes="type === 'dark' ? 'thead-dark': 'thead-light'"
                  tbody-classes="list"
                  :data="noticeData">
        <template slot="columns">
          <th>숙제명</th>
          <th>D-Day</th>
          <th>숙제유형</th>
          <th>상태</th>
          <th>마감일</th>
        </template>

        <template slot-scope="{row}">
          <th scope="row">
            <div class="media align-items-center">
              <!--a href="#" class="avatar rounded-circle mr-3">
                <img alt="Image placeholder" :src="row.img">
              </a-->
              <div class="media-body">
                <span class="name mb-0 text-sm" @click="clickNotiDeatail(row.id)">{{row.title}}</span>
              </div>
            </div>
          </th>
          <td class="budget">
            {{row.d_day}}
          </td>
          <td v-if="row.type=='OPEN'">
            <badge class="badge-dot mr-4" type="info">
              <span class="status">공개</span>
            </badge>
            <!--badge class="badge-dot mr-4" :type="row.statusType">
              <i :class="`bg-${row.statusType}`"></i>
              <span class="status">{{row.status}}</span>
            </badge-->
          </td>
          <td v-else>
            <badge class="badge-dot mr-4" type="warning">
              <span class="status">비공개</span>
            </badge>
          </td>


          <td v-if="row.status=='PENDING'">
            <badge class="badge-dot mr-4" type="warning">
              <i class="bg-warning"></i>
              <span class="status">대기</span>
            </badge>
          </td>
          <td v-else-if="row.status=='PROGRESS'">
            <badge class="badge-dot mr-4" type="info">
              <i class="bg-info"></i>
              <span class="status">진행중</span>
            </badge>
          </td>
          <td v-else-if="row.status=='COMPLETED'">
            <badge class="badge-dot mr-4" type="success">
              <i class="bg-success"></i>
              <span class="status">완료</span>
            </badge>
          </td>
          <td v-else>
            <badge class="badge-dot mr-4" type="danger">
              <i class="bg-danger"></i>
              <span class="status">취소됨</span>
            </badge>
          </td>


          <td>
            {{row.expiredAt}}
          </td>

          <!--td>
            <div class="avatar-group">
              <a href="#" class="avatar avatar-sm rounded-circle" data-toggle="tooltip" data-original-title="Ryan Tompson">
                <img alt="Image placeholder" src="img/theme/team-1-800x800.jpg">
              </a>
              <a href="#" class="avatar avatar-sm rounded-circle" data-toggle="tooltip" data-original-title="Romina Hadid">
                <img alt="Image placeholder" src="img/theme/team-2-800x800.jpg">
              </a>
              <a href="#" class="avatar avatar-sm rounded-circle" data-toggle="tooltip" data-original-title="Alexander Smith">
                <img alt="Image placeholder" src="img/theme/team-3-800x800.jpg">
              </a>
              <a href="#" class="avatar avatar-sm rounded-circle" data-toggle="tooltip" data-original-title="Jessica Doe">
                <img alt="Image placeholder" src="img/theme/team-4-800x800.jpg">
              </a>
            </div>
          </td-->

          <!--td>
            <div class="d-flex align-items-center">
              <span class="completion mr-2">{{row.completion}}%</span>
              <div>
                <base-progress :type="row.statusType"
                               :show-percentage="false"
                               class="pt-0"
                               :value="row.completion"/>
              </div>
            </div>
          </td>

          <td class="text-right">
            <base-dropdown class="dropdown"
                           position="right">
              <a slot="title" class="btn btn-sm btn-icon-only text-light" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <i class="fas fa-ellipsis-v"></i>
              </a>

              <template>
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <a class="dropdown-item" href="#">Something else here</a>
              </template>
            </base-dropdown>
          </td-->

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
<script>
  import {BUS} from "../EventBus";

  const axios = require('axios');

  export default {
    name: 'projects-table',
    props: {
      type: {
        type: String
      },
      title: String,
      noticeData: Array,
      subjectMasterId: String,
      subjectId: String,
      subjectPivotNameEdit: String,
      nowUser: String
    },
    data() {
      return {
        modals: false,
        modals3: false,
        responseMsg: false,
        tableData: [
          {
            img: 'img/theme/bootstrap.jpg',
            title: '2차 함수 응용',
            budget: '50점',
            status: '평가진행중',
            statusType: 'warning',
            completion: 60
          },
          {
            img: 'img/theme/angular.jpg',
            title: '2차 방정식',
            budget: '40점',
            status: '평가완료',
            statusType: 'success',
            completion: 100
          },
          {
            img: 'img/theme/sketch.jpg',
            title: '1차 수행평가',
            budget: '100점',
            status: '연기',
            statusType: 'danger',
            completion: 72
          },
          {
            img: 'img/theme/react.jpg',
            title: '지수함수와 로그',
            budget: '20',
            status: '진행 중',
            statusType: 'info',
            completion: 90
          },
          {
            img: 'img/theme/vue.jpg',
            title: '행렬의 곱',
            budget: '10',
            status: '평가완료',
            statusType: 'success',
            completion: 100
          }
        ]
      }
    },
    methods : {
      clickChangeSunjectInfoButton(){
        BUS.$emit('subjectUpdate', true);
        console.log("이벤트버스: BUS.$emit('subjectUpdate', true);");
      },
      clickNotiDeatail(id){
        BUS.$emit('goNotiDetail', id);
      },
      clickAddNotiButton(){
        BUS.$emit('createNoti', true);
      },
      updateSubjectReq(){
        let vm = this;
        const axiosConfig = { headers:{ "Content-Type": "application/json"} };

        axios.put('/api/subjects',
                '{' +
                '"subjectName": "' + vm.subjectPivotNameEdit + '",' +
                '"subjectId": "' + vm.subjectId + '"' +
                '}'
                //vm.createSubjectName
                //form
                , axiosConfig)
                .then(function(response){
                  if(response.data.statusCode == 'OK'){
                    vm.modals3 = false;
                    vm.modals = true;
                    vm.responseMsg = '성공하였습니다.';
                    BUS.$emit('subjectUpdate', '');
                  }
                  else{
                    vm.modals = true;
                    vm.responseMsg = response.data.message;
                  }
                });
      },
      deleteSubjectReq(){
        alert("준비중 입니다.");
      }
    }
  }
</script>
<style>
</style>
