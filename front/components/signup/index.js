import style from './signup.module.css';
import { useState, useEffect, useRef } from 'react';

const ID_REGEX = /^[a-zA-Z0-9]{3,20}$/;
const PW_REGEX = /^[a-zA-Z0-9]{8,24}$/;

export default function Signup ({toMain}) {
  
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

  const [errorMsg, setErrorMsg] = useState([]);
  
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
    setErrorMsg([]);
  };

  const userIdCheck = (e) => {         // 아이디 중복 확인
    e.preventDefault();
    if (validUserId && userId) {
      fetch(`https://k6a401.p.ssafy.io/api/user/id?userId=${userId}`)
        .then((response) => response.json())
        .then((data) => {
          if (data) {
            alert('유효한 아이디입니다');
            setAvailableUserId(data);
          }
          else {
            alert('이미 사용중인 아이디입니다');
          }
        });
    }
    else {
      alert('유효한 아이디를 입력해주세요');
    }
  }

  const submitRegistration = () => {    // 백단에 회원가입 요청
    fetch('https://k6a401.p.ssafy.io/api/user', {
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
          alert(`회원가입 성공! : ${data}`);
          initializeData();
          toMain();
        } else {
          alert(`회원가입 실패 : ${data}`);
        }
      })
      .catch((error) => {
        alert(`회원가입 실패 : ${error}`);
      });
  };

  const finalCheck = () => {          // 회원가입 버튼 눌렀을때 로직
    if (availableUserId && validUserPassword && validMatchPassword) {
      submitRegistration();
      
    }
    else {
      if (!availableUserId) {
        addErrorID();
      }
      if (!validUserPassword) {
        addErrorPW();
      }
      if (!validMatchPassword) {
        addErrorConfirmPW();
      }
      else if (errorMsg && !errorMsg.includes("잘못된 접근입니다")) {
        setErrorMsg(errorMsg + ['잘못된 접근입니다']);
      }

      alert(errorMsg);
      
    }
  }

  const addErrorID = () => {
    if (errorMsg && !errorMsg.includes("confirm your ID")) {
      setErrorMsg(errorMsg + ["confirm your ID"]);
    }
    else if (!errorMsg) {
      setErrorMsg(["confirm your ID"]);
    }
  }
  const removeErrorID = () => {
    if (errorMsg && errorMsg.includes("confirm your ID")) {
      errorMsg.filter(msg => msg != "confirm your ID");
    }
  }
  const addErrorPW = () => {
    if (errorMsg && !errorMsg.includes("inconsistent password")) {
      setErrorMsg(errorMsg + ["inconsistent password"]);
    }
    else if (!errorMsg) {
      setErrorMsg(["inconsistent password"]);
    }
  }
  const removeErrorPW = () => {
    if (errorMsg && errorMsg.includes("confirm your password")) {
      errorMsg.filter(msg => msg != "confirm your password");
    }
  }
  const addErrorConfirmPW = () => {
    if (errorMsg && !errorMsg.includes("confirm your password")) {
      setErrorMsg(errorMsg + ["confirm your password"]);
    }
    else if (!errorMsg) {
      setErrorMsg(["confirm your password"]);
    }
  }
  const removeErrorConfirmPW = () => {
    if (errorMsg && errorMsg.includes("inconsistent password")) {
      Object.values(errorMsg).filter(msg => msg != "inconsistent password");
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
    console.log(validUserId);
    setAvailableUserId(false);
    if (result) {
      removeErrorID();
    }
  }, [userId]);

  useEffect(() => {                   // 비밀번호 조건 충족 여부 확인
    const result = PW_REGEX.test(userPassword);
    setValidUserPassword(result);
    if (result) {
      removeErrorPW();
    }
  }, [userPassword]);

  useEffect(() => {                   // 비밀번호학인 조건 충족 여부 확인
    const match = userPassword === matchPassword;
    setValidMatchPassword(match);
    if (match) {
      removeErrorConfirmPW();
    }
  }, [matchPassword]);

  useEffect(() => {                   // 컴포넌트 렌더 시 ID입력값에 focus
    inputID.current.focus();
  }, [])

  return (
    <main className={style.signupContainer}>
      <div className={style.semiContainer}>

        <h2>
          signup
        </h2>
        <section className={style.signup}>
          <div className={style.card}>
            <label>ID 
              <input type="text" onChange={e => setUserId(e.target.value)} ref={inputID} placeholder="a-z,A-Z,0-9 / 3~20" required />
              <button className={style.checkBtn} onClick={userIdCheck}>check ID</button>
            </label>
          </div>
          <div className={style.card}>
            <label>Password <input type="password" onChange={e => setUserPassword(e.target.value)} ref={pw} placeholder="a-z,A-Z,0-9 / 8~24" required /></label>
          </div>
          <div className={style.card}>
            <label>PW confirm <input type="password" onChange={e => setMatchPassword(e.target.value)} ref={pwConf} required /></label>
          </div>
        </section>
        <div className={style.btnGroup}>
          <button className={style.signupBtn} onClick={finalCheck}>Signup!</button>
          <button className={style.backBtn} onClick={goBack} >back</button>
        </div>
      </div>
    </main>
  )

}