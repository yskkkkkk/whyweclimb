import style from './signup.module.css';

export default function Signup ({toLogin}) {
  
  return (
    <main className={style.container}>
      <h2>
        signup
      </h2>
      <section className={style.login}>
        <div className={style.card}>
          <label>Id <input type="text" /></label>
        </div>
        <div className={style.card}>
          <label>Password <input type="password" /></label>
        </div>
        <div className={style.card}>
          <label>PW confirm <input type="password" /></label>
        </div>
      </section>
      <a href="#" className={style.btns} onClick={toLogin}>back</a>
    </main>
  )

}