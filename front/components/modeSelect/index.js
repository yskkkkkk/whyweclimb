import style from './modeSelect.module.css';
import Link from 'next/link';


export default function ModeSelect({toMain, toConfigure}) {
  
  // const test = (e) => {
  //   e.preventDefault();

  //   const token = localStorage.getItem("token");
  //   console.log(token);
  //   const headers = {
  //     'Authorization': token,
  //     mode: 'no-cors'
  //   }
  //   fetch('https://k6a401.p.ssafy.io/api/user/information', {headers: headers})
  //   .then((response) => {
  //     console.log(response.json());
  //   });
  // };



  return (
    <main className={style.container}>
      <section className={style.container2}>
        <a className={style.btn} href={'/single/singleGame'}>
          <div className={style.stage}>
            <img className={`${style.box} ${style.bounce7}`} src="/images/running_R1.png" alt="a character jumping image" />
          </div>
          <h2>single mode</h2>
        </a>
        <a className={style.btn1} href={'/multi'}>
          <div className={style.stage1}>
            {/* https://css-tricks.com/making-css-animations-feel-natural/ 참고 */}
            <img className={`${style.box1} ${style.bounce1}`} src="/images/sourceror.png" alt="a character jumping image" />
            <img className={`${style.box1} ${style.bounce2}`} src="/images/dwarf.png" alt="a character jumping image" />
            <img className={`${style.box1} ${style.bounce3}`} src="/images/woodElf.png" alt="a character jumping image" />
          </div>
          <h2 className={style.glow}>multi mode</h2>
        </a>
      </section>
      {/* <button onClick={toConfigure}>configure</button> */}
      <button className={style.btnBack} onClick={toMain}>logout</button>
    </main>
  )
}