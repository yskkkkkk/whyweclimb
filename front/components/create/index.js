import style from './create.module.css';

export default function Create({toLobby}) {

  

  return (
    <main className={style.container}>
      <section className={style.choice}>
        <span>Interference</span>
        <div>
          <label>
            <input type="radio" name="interference" value="on" required />
            on
          </label>
          <label>
            <input type="radio" name="interference" value="off" required />
            off
          </label>
        </div>
      </section>
      <section className={style.choice}>
        <span>Private Room</span>
        <div>
          <label>
            <input type="radio" name="privateRoom" value="yes" required />
            yes
          </label>
          <label>
            <input type="radio" name="privateRoom" value="no" required />
            no
          </label>
        </div>
      </section>
      <section className={style.choice}>
        <span>Max player number</span>
        <div>
          <label>
            <input type="radio" name="playerNumber" value="2" required />
            2
          </label>
          <label>
            <input type="radio" name="playerNumber" value="3" required />
            3
          </label>
          <label>
            <input type="radio" name="playerNumber" value="4" required />
            4
          </label>
        </div>
      </section>
    </main>
  )
}