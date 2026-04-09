import { useState, useRef, useEffect } from 'react';
import style from './login.module.css';
import toast from 'react-hot-toast';
import { useLanguage } from '../../context/LanguageContext';


export default function Login({toSignup, toModeSelect, openUCC}) {

  const { t } = useLanguage();

  const inputID = useRef();
  const pw = useRef();

  const [userID, setUserID] = useState("");
  const [userPassword, setUserPassword] = useState("");

  const initializeData = () => {        // 로그인 입력 정보의 상태들 초기화
    inputID.current.value = '';
    pw.current.value = '';
    setUserID("");
    setUserPassword("");
  };

  const toSignUp = () => {
    initializeData();
    toSignup();
  }

  const handleLoginSubmit = () => {     // 로그인 버튼 누를시 post요청 (백에서 실패사유 알려주면 땡큐)
    let flag_conf = false
    fetch(`${process.env.NEXT_PUBLIC_API_URL}/user/login`, {
      method: "POST",
      body: JSON.stringify({
        userId: userID,
        userPassword: userPassword,
      }),
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    })
    .then((response) => {
      // console.log(response)
      if(response.status==409){
        toast.error(t('already_logged_in'))
        flag_conf=true
      }
      return response.json();
    })
    .then((data) => {
      // console.log(data);
      if (data.token) {
        sessionStorage.setItem("token", data.token);
        initializeData();
        // alert(`로그인 성공 : ${data}`)
        toModeSelect();
      }
      else {
        if(!flag_conf){
          toast.error(t('invalid_login'));
        }
      }
    })
    .catch((error) => {
      toast.error(`${t('login_failed')}${error}`);
    });
  };

  const onKeyPress=(e) => {
    if(e.key=='Enter'){
      handleLoginSubmit();
    }
  }

  useEffect(() => {
    inputID.current.focus();
  }, [])


  return (
    <main className={style.container}>
      <div className={style.title}>why we climb</div>
      
      <div className={style.loginContainer}> 
        <section className={style.login}>

          <div className={style.trailerMascot} onClick={openUCC}>
            <span />
          </div>

          <div className={style.fonts}>{t('login')}</div>
          <div className={style.card}>
            <label  className={style.smallfonts}>{t('id')}
              <input type="text" onChange={e => setUserID(e.target.value)} ref={inputID} required />
            </label>
          </div>
          <div className={style.card}>
            <label className={style.smallfonts}>{t('password')}
              <input type="password" onChange={e => setUserPassword(e.target.value)} ref={pw} required onKeyDown={onKeyPress}/>
            </label>
          </div>
          <div className={style.btnGroup}>
            <button className={style.loginBtn} onClick={handleLoginSubmit} >{t('login')}</button>
            <div className={style.signBtnGroup}>
              <div className={style.btns}>{t('no_account')}</div>
              <button className={style.signupBtn} onClick={toSignUp} >{t('signup')}</button>
            </div>
          </div>
        </section>
      </div>
    </main>
  )
}