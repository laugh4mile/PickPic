<template>
    <v-dialog v-model="dialog" width="300" content-class="elevation-0">
      <template v-slot:activator="{ on }">
        <v-btn
          text
          v-on="on"
          :hidden="btnView"
          class="btn btn-outline-secondary ma-4 rounded-pill font-yg-jalnan"
          style="border-width : 3px; font-size : 12 px; "
        >
          로그인
        </v-btn>
      </template>

      <!-- 로그인모달 시작 -->
      <div
        class="d-flex justify-content-center h-100 w-100 rounded-xl font-yg-jalnan"
      >
        <div class="card rounded-xl" style="background : white;">
          <div class="card-header ">
            <h3 style="color:black; text-align:center" class="ma-0">
              PickPic
            </h3>
            <!-- <div class="d-flex justify-content-end social_icon">
                      <span><i class="fab fa-facebook-square"></i></span>
                      <span><i class="fab fa-google-plus-square"></i></span>
                      <span><i class="fab fa-twitter-square"></i></span>
                    </div> -->
          </div>
          <div class="card-body font-ELAND_Choice_M">
            <form>
              <span class="badge badge-danger mt-1" v-if="!availableEmailForm"
                >이메일 형식이 다릅니다.</span
              >
              <div class="input-group form-group">
                <!-- <div class="input-group-prepend">
                          <span class="input-group-text"
                            ><i class="fas fa-user"></i
                          ></span>
                        </div> -->
                <input
                  type="email"
                  class="form-control rounded-xl"
                  v-model="user.email"
                  placeholder="이메일"
                  @blur="checkDuplicate"
                />
              </div>
              <div class="input-group form-group">
                <!-- <div class="input-group-prepend">
                          <span class="input-group-text"
                            ><i class="fas fa-key"></i
                          ></span>
                        </div> -->
                <input
                  type="password"
                  class="form-control rounded-xl"
                  v-model="user.pwd"
                  placeholder="비밀번호"
                />
              </div>
              <!-- <div class="row align-items-center remember">
                          <input type="checkbox">Remember Me
                        </div> -->
              <input
                type="submit"
                @click="login"
                value="로그인"
                class="btn btn-secondary rounded-pill form-control font-yg-jalnan"
              />
            </form>
          </div>
          <div class="card-footer rounded-b-xl">
            <div
              class="d-flex justify-content-center links text-secondary font-ELAND_Choice_M"
            >
              처음이신가요 ?
              <a href="regist" class="font-ELAND_Choice_B">회원가입</a>
            </div>
          </div>
        </div>
      </div>
      <!-- 로그인 모달끝 -->
    </v-dialog>
</template>
<script>
import { mapGetters, mapMutations } from 'vuex';
export default {
    data(){
        return {
            user: {
                email: '',
                pwd: '',
            },
            availableEmailForm: true,
        };
    },
    props:{
        dialog: {
            default: false,
            type: Boolean,
        },
        btnView:{
            default: false,
            type: Boolean,
        }
    },
      computed: {
    ...mapGetters([
      'links',
      'getAccessToken',
      'getUserEmail',
      'getUserName',
      'getRole',
    ]),
  },
    methods:{
        validEmail(email) {
            var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(email);
        },
        checkDuplicate() {
         //이메일 유효성을 검사한다.
            if (!this.validEmail(this.user.email)) {
                //유효성이 틀리다면 data 값을 false로 한다.
                this.availableEmailForm = false;
            } else {
                this.availableEmailForm = true;
            }
            },
            ...mapMutations(['toggleDrawer']),
            onClick(e, item) {
            this.$router.push(item.href).catch((error) => {
                if (error.name === 'NavigationDuplicated') {
                // 같은 경로 클릭시 새로고침 되게
                location.reload();
                }
            });
        },
        login: function() {
            event.preventDefault();
            this.dialog = false;
            // LOGIN 액션 실행
            // 서버와 통신(axios)을 해 토큰값을 얻어야 하므로 Actions를 호출.
            this.$store.dispatch('LOGIN', this.user);
            console.log(this.$store.getters.getAccessToken);
            this.$emit('loginSuccess');
            this.user.email = '';
            this.user.pwd = '';
        },
        logout() {
            this.$store
            .dispatch('LOGOUT')
            .then(() => this.$router.replace('/').catch(() => {}));
            console.log(localStorage);
            localStorage.clear;
            console.log(localStorage);
        },
        registForm() {
            this.$router.push('/regist');
        },
    }
}
</script>