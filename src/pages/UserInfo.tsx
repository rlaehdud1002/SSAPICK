const UserInfo = () =>{
    return <form className="flex flex-col items-center">
        <img src="../images/icons/profile.png" className="w-28 py-5"/>
        <div>
            <div>
            <label htmlFor="name">이름 </label>
            <input className="rounded-md bg-white/50 border border-indigo-950" type="text" name="name"/>
            </div>
            <div>
            <label htmlFor="gender">성별 </label>
            <select className="rounded-md bg-white/50 border border-indigo-950" name="gender">
                <option value="default">-</option>
                <option value="woman">여자</option>
                <option value="man">남자</option>
            </select>
            </div>
            <div>
            <label htmlFor="th">기수 </label>
            <select className="rounded-md bg-white/50 border border-indigo-950" name="th">
                <option value="default">-</option>
                <option value="11th">11기</option>
                <option value="12th">12기</option>
            </select>
            </div>
            <div>
            <label htmlFor="campus">캠퍼스 </label>
            <select className="rounded-md bg-white/50 border border-indigo-950" name="campus">
                <option value="default">-</option>
                <option value="seoul">서울</option>
                <option value="daejeon">대전</option>
                <option value="gwangju">광주</option>
                <option value="booulgyeong">부울경</option>
                <option value="gumi">구미</option>
            </select>
            </div>
            <div>
            <label htmlFor="class">반 </label>
            <select className="rounded-md bg-white/50 border border-indigo-950" name="class">
                <option value="default">-</option>
                <option value="first">1반</option>
                <option value="second">2반</option>
                <option value="third">3반</option>
                <option value="fourth">4반</option>
                <option value="fifth">5반</option>
            </select>
            </div>
            <div>
                <button type="button" className="text-white bg-blue-500 font-medium rounded-lg text-sm px-5 py-2.5 ">다음</button>
            </div>
        </div>
    </form>
}
export default UserInfo