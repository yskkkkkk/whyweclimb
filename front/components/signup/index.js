import style from './signup.module.css';
import { useState, useEffect, useRef } from 'react';
import toast from 'react-hot-toast';
import { useLanguage } from '../../context/LanguageContext';

const ID_REGEX = /^[a-zA-Z0-9]{3,20}$/;
const PW_REGEX = /^[a-zA-Z0-9]{8,24}$/;

export default function Signup ({toMain}) {
  
  const { t } = useLanguage();

  const inputID = useRef();
  const pw = useRef();
  const pwConf = useRef();
  
  const [userId, setUserId] = useState("");
  const [validUserId, setValidUserId] = useState(false);
  const [availableUserId, setAvailableUserId] = useState(false);

  const [userPassword, setUserPassword] = useState("");
  const [validUserPassword, setValidUserPassword] = useState(false);

  const [matchPassword, setMatchPassword] = useState("");
  const [validMatchPassword, setValidMatchPassword] = useState(false);
  
  const [errorMsg, setErrorMsg] = useState("");

  const initializeData = () => {      // 모든 상태정보 초기화
    inputID.current.value = '';
    pw.current.value = '';
    pwConf.current.value = '';
    setUserId("");
    setUserPassword("");
    setMatchPassword("");
    setValidUserId(false);
    setAvailableUserId(false);
    setValidUserPassword(false);
    setValidMatchPassword(false);
    setErrorMsg("");
  };

  const userIdCheck = (e) => {         // 아이디 중복 확인
    e.preventDefault();
    if (validUserId && userId) {
      fetch(`${process.env.NEXT_PUBLIC_API_URL}/user/id?userId=${userId}`)
        .then((response) => response.json())
        .then((data) => {
          if (data) {
            toast.success(t('id_available'));
            setAvailableUserId(data);
          }
          else {
            toast.error(t('id_in_use'));
          }
        });
    }
    else {
      toast.error(t('provide_valid_id'));
    }
  }

  const submitRegistration = () => {    // 백단에 회원가입 요청
    fetch(`${process.env.NEXT_PUBLIC_API_URL}/user`, {
      method: "POST",
      body: JSON.stringify({
        userId: userId,
        userPassword: userPassword,
      }),
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        if (data) {
          toast(t('welcome'), {icon: "🎉"});
          initializeData();
          toMain();
        } else {
          toast.error(t('unexpected_error'));
        }
      })
      .catch((error) => {
        toast.error(`${t('signup_failed')}${error}`);
      });
  };

  // 왜 setErrorMsg 코드가 적용 안될까.. 
  const finalCheck = () => {          // 회원가입 버튼 눌렀을때 로직
    setErrorMsg(t('check_followings'));
    let errMsg = ""
    if (availableUserId && validUserPassword && validMatchPassword) {
      submitRegistration();
    }
    else {
      if (!availableUserId) {
        errMsg+='ID\n'
        
      }
      if (!validUserPassword) {
        errMsg+='Password\n'
        
      }
      if (!validMatchPassword) {
        errMsg+='Password confirm'
        
      }
      
      toast.error(errMsg);
    }
  }

  

  const goBack = () => {
    initializeData();
    toMain();
  }

  useEffect(() => {                   // 아이디 조건 충족 여부 확인
    // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/RegExp/test
    const result = ID_REGEX.test(userId);
    setValidUserId(result);
    setAvailableUserId(false);
  }, [userId]);

  useEffect(() => {                   // 비밀번호 조건 충족 여부 확인
    const result = PW_REGEX.test(userPassword);
    setValidUserPassword(result);
  }, [userPassword]);

  useEffect(() => {                   // 비밀번호학인 조건 충족 여부 확인
    const match = userPassword === matchPassword;
    setValidMatchPassword(match);
  }, [matchPassword]);

  useEffect(() => {                   // 컴포넌트 렌더 시 ID입력값에 focus
    inputID.current.focus();
  }, [])

  return (
    <main className={style.signupContainer}>
      <div className={style.semiContainer}>

        <div className={style.fonts}>
          {t('signup')}
        </div>
        <section className={style.signup}>
          <div className={style.card}>
            <label className={style.smallfonts}>{t('id')}
              <input type="text" onChange={e => setUserId(e.target.value)} ref={inputID} placeholder="a-z, A-Z, 0-9 / 3~20" required />
              <button className={style.checkBtn} onClick={userIdCheck}>{t('check_id')}</button>
            </label>
          </div>
          <div className={style.card}>
            <label className={style.smallfonts}>{t('password')} <input type="password" onChange={e => setUserPassword(e.target.value)} ref={pw} placeholder="a-z, A-Z, 0-9 / 8~24" required /></label>
          </div>
          <div className={style.card}>
            <label className={style.smallfonts}>{t('pw_confirm')} <input type="password" onChange={e => setMatchPassword(e.target.value)} ref={pwConf} required /></label>
          </div>
        </section>
        <div className={style.btnGroup}>
          <button className={style.signupBtn} onClick={finalCheck}>{t('signup_exclamation')}</button>
          <button className={style.backBtn} onClick={goBack}>{t('back')}</button>
        </div>
      </div>
    </main>
  )

}